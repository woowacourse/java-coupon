package coupon.config;

import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
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


    private static final String READ_DATASOURCE = "readDataSource";
    private static final String WRITE_DATASOURCE = "writeDataSource";
    private static final String ROUTE_DATASOURCE = "routeDataSource";

    @Bean(name = READ_DATASOURCE)
    @ConfigurationProperties(prefix = "coupon.datasource.reader")
    public DataSource readDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = WRITE_DATASOURCE)
    @ConfigurationProperties(prefix = "coupon.datasource.writer")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = ROUTE_DATASOURCE)
    @DependsOn({READ_DATASOURCE, WRITE_DATASOURCE})
    public DataSourceRouter routeDataSource(
            @Qualifier(WRITE_DATASOURCE) DataSource writeDataSource,
            @Qualifier(READ_DATASOURCE) DataSource readDataSource
    ) {
        DataSourceRouter dataSourceRouter = new DataSourceRouter();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceType.READER, readDataSource);
        dataSourceMap.put(DataSourceType.WRITER, writeDataSource);

        dataSourceRouter.setTargetDataSources(dataSourceMap);
        dataSourceRouter.setDefaultTargetDataSource(writeDataSource);
        return dataSourceRouter;
    }

    @Bean
    @Primary
    @DependsOn(ROUTE_DATASOURCE)
    public DataSource defaultDataSource(@Qualifier(ROUTE_DATASOURCE) DataSource routeDataSource) {
        return new LazyConnectionDataSourceProxy(routeDataSource);
    }
}
