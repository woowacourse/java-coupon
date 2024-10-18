package coupon.config;

import java.util.Map;
import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Configuration
public class DataSourceConfig {

    @Primary
    @Bean
    public DataSource currentDataSource() {
        return new LazyConnectionDataSourceProxy(routingDataSource());
    }

    @Bean
    public DataSource routingDataSource() {
        RoutingDataSource routingDataSource = new RoutingDataSource();

        Map<Object, Object> dataSources = Map.of(
                DataSourceType.WRITER, writerDataSource(),
                DataSourceType.READER, readerDataSource()
        );
        routingDataSource.setTargetDataSources(dataSources);
        routingDataSource.setDefaultTargetDataSource(writerDataSource());

        return routingDataSource;
    }

    @Bean
    @ConfigurationProperties(prefix = "coupon.datasource.writer")
    public DataSource writerDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "coupon.datasource.reader")
    public DataSource readerDataSource() {
        return DataSourceBuilder.create().build();
    }
}
