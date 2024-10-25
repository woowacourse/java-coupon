package coupon.infra;

import javax.sql.DataSource;
import java.util.Map;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Configuration
public class DataSourceConfig {

    private static final String WRITE_DATA_SOURCE = "writeDataSource";
    private static final String READ_DATA_SOURCE = "readDataSource";
    private static final String ROUTE_DATA_SOURCE = "routeDataSource";

    @Bean
    @ConfigurationProperties(prefix = "coupon.datasource.writer")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @ConfigurationProperties(prefix = "coupon.datasource.reader")
    public DataSource readDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @DependsOn({WRITE_DATA_SOURCE, READ_DATA_SOURCE})
    public DataSource routeDataSource(
            @Qualifier(WRITE_DATA_SOURCE) DataSource writeDataSource,
            @Qualifier(READ_DATA_SOURCE) DataSource readDataSource
    ) {
        DataSourceRouter dataSourceRouter = new DataSourceRouter();
        Map<Object, Object> dataSourceMap = Map.of(
                DataSourceType.WRITER, writeDataSource,
                DataSourceType.READER, readDataSource
        );
        dataSourceRouter.setTargetDataSources(dataSourceMap);
        dataSourceRouter.setDefaultTargetDataSource(writeDataSource);

        return dataSourceRouter;
    }

    @Bean
    @Primary
    @DependsOn(ROUTE_DATA_SOURCE)
    public DataSource dataSource(@Qualifier(ROUTE_DATA_SOURCE) DataSource routeDataSource) {
        return new LazyConnectionDataSourceProxy(routeDataSource);
    }
}
