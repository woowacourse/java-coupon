package coupon.config;

import java.util.HashMap;
import java.util.List;
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

    @Bean
    @ConfigurationProperties(prefix = "coupon.datasource.writer")
    public DataSource sourceDataSource() {
        return DataSourceBuilder.create()
                .build();
    }

    @Bean
    @ConfigurationProperties(prefix = "coupon.datasource.reader")
    public DataSource replicaDataSource() {
        return DataSourceBuilder.create()
                .build();
    }

    @Bean
    public DataSource routingDataSource(DataSource sourceDataSource, DataSource replicaDataSource) {
        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put("source", sourceDataSource);
        dataSources.put("replica", replicaDataSource);

        RoutingDataSource routingDataSource = new RoutingDataSource(List.of("replica"));
        routingDataSource.setDefaultTargetDataSource(dataSources.get("source"));
        routingDataSource.setTargetDataSources(dataSources);
        return replicaDataSource;
    }

    @Primary
    @Bean
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(routingDataSource(sourceDataSource(), replicaDataSource()));
    }
}
