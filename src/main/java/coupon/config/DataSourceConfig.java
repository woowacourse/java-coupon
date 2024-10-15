package coupon.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.coupon.reader")
    public DataSource readDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @ConfigurationProperties("spring.coupon.writer")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @DependsOn({"readDataSource", "writeDataSource"})
    public DataSource routeDataSource() {
        DataSourceRouter router = new DataSourceRouter();

        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put("reader", readDataSource());
        dataSources.put("writer", writeDataSource());
        router.setTargetDataSources(dataSources);

        return router;
    }

    @Bean
    @DependsOn("routeDataSource")
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(routeDataSource());
    }
}
