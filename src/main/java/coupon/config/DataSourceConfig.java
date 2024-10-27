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
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Configuration
public class DataSourceConfig {

    public static final String READ_DATASOURCE = "readDataSource";
    public static final String WRITE_DATASOURCE = "writeDataSource";

    @Bean(READ_DATASOURCE)
    @ConfigurationProperties(prefix = "coupon.datasource.reader")
    public DataSource readDataSource() {
        return createDataSource();
    }

    @Bean(WRITE_DATASOURCE)
    @ConfigurationProperties(prefix = "coupon.datasource.writer")
    public DataSource writeDataSource() {
        return createDataSource();
    }

    @Bean
    public DataSourceRouter routeDataSource(
            @Qualifier(READ_DATASOURCE) DataSource readDataSource,
            @Qualifier(WRITE_DATASOURCE) DataSource writeDataSource) {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(READ_DATASOURCE, readDataSource);
        dataSourceMap.put(WRITE_DATASOURCE, writeDataSource);

        DataSourceRouter dataSourceRouter = new DataSourceRouter();
        dataSourceRouter.setTargetDataSources(dataSourceMap);
        dataSourceRouter.setDefaultTargetDataSource(writeDataSource);
        return dataSourceRouter;
    }

    @Bean
    @Primary
    public DataSource dataSource(DataSource routeDataSource) {
        return new LazyConnectionDataSourceProxy(routeDataSource);
    }

    private DataSource createDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }
}
