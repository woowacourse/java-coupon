package coupon.coupon.config;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Configuration
public class DatasourceConfig {

    private static final String WRITE_DATA_SOURCE_NAME = "writeDataSource";
    private static final String READ_DATA_SOURCE_NAME = "readDataSource";
    private static final String ROUTING_DATA_SOURCE_NAME = "routingDataSource";

    @Bean(name = WRITE_DATA_SOURCE_NAME)
    @ConfigurationProperties(prefix = "coupon.datasource.writer")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = READ_DATA_SOURCE_NAME)
    @ConfigurationProperties(prefix = "coupon.datasource.reader")
    public DataSource readDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    DataSource routingDataSource(
            @Qualifier(WRITE_DATA_SOURCE_NAME) DataSource writeDataSource,
            @Qualifier(READ_DATA_SOURCE_NAME) DataSource readDataSource) {
        AbstractRoutingDataSource routingDataSource = new TransactionalDataSourceRouter();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceType.WRITER, writeDataSource);
        dataSourceMap.put(DataSourceType.READER, readDataSource);

        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(writeDataSource);

        return routingDataSource;
    }

    @Primary
    @Bean
    public DataSource dataSource(@Qualifier(ROUTING_DATA_SOURCE_NAME) DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
}
