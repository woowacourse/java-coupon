package coupon.config;

import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Configuration
public class ReplicationDataSourceConfig {

    private static final String WRITE_DATASOURCE = "writeDataSource";
    private static final String READ_DATASOURCE = "readDataSource";
    private static final String ROUTING_DATASOURCE = "routingDataSource";

    @Bean(name = WRITE_DATASOURCE)
    @ConfigurationProperties(prefix = "coupon.datasource.writer")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = READ_DATASOURCE)
    @ConfigurationProperties(prefix = "coupon.datasource.reader")
    public DataSource readDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = ROUTING_DATASOURCE)
    DataSource routingDataSource(@Qualifier(READ_DATASOURCE) DataSource readDataSource,
                                 @Qualifier(WRITE_DATASOURCE) DataSource writeDataSource) {
        ReadOnlyDataSourceRouter dataSourceRouter = new ReadOnlyDataSourceRouter();
        Map<Object, Object> dataSources = Map.of(
                DataSourceType.READER, readDataSource,
                DataSourceType.WRITER, writeDataSource
        );
        dataSourceRouter.setTargetDataSources(dataSources);
        dataSourceRouter.setDefaultTargetDataSource(writeDataSource);
        return dataSourceRouter;
    }

    @Bean
    @Primary
    public DataSource dataSource(@Qualifier(ROUTING_DATASOURCE) DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
}
