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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Configuration
@EnableJpaRepositories(basePackages = "coupon")
public class DataSourceConfig {

    @Bean(name = "writerDataSource")
    @ConfigurationProperties(prefix = "coupon.datasource.writer")
    public DataSource writerDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "readerDataSource")
    @ConfigurationProperties(prefix = "coupon.datasource.reader")
    public DataSource readerDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "routingDataSource")
    @DependsOn({"writerDataSource", "readerDataSource"})
    public DataSource routingDataSource() {
        DataSource writerDataSource = writerDataSource();
        DataSource readerDataSource = readerDataSource();
        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put("writer", writerDataSource);
        dataSources.put("reader", readerDataSource);
        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setTargetDataSources(dataSources);
        routingDataSource.setDefaultTargetDataSource(writerDataSource);
        return routingDataSource;
    }

    @Bean
    @Primary
    @DependsOn("routingDataSource")
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(routingDataSource());
    }
}
