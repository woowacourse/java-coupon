package coupon.config;

import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
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
    @ConfigurationProperties(prefix = "coupon.datasource.writer")
    public DataSource writerDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = WRITE_DATASOURCE)
    @ConfigurationProperties(prefix = "coupon.datasource.reader")
    public DataSource readerDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = ROUTE_DATASOURCE)
    @DependsOn({READ_DATASOURCE, WRITE_DATASOURCE})
    public DataSource routingDataSource() {
        DataSourceRouter dataSourceRouter = new DataSourceRouter();

        Map<Object, Object> dataSources = new HashMap<>(Map.of(
                DataSourceRouter.READ_DATASOURCE_KEY, readerDataSource(),
                DataSourceRouter.WRITE_DATASOURCE_KEY, writerDataSource()
        ));
        dataSourceRouter.setTargetDataSources(dataSources);
        dataSourceRouter.setDefaultTargetDataSource(writerDataSource());

        return dataSourceRouter;
    }

    @Bean
    @Primary
    @DependsOn(ROUTE_DATASOURCE)
    public DataSource defaultDataSource() {
        return new LazyConnectionDataSourceProxy(routingDataSource());
    }
}
