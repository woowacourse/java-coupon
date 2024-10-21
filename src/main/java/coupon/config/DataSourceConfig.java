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
public class DataSourceConfig {

    public static final String SOURCE_SERVER = "source";
    public static final String REPLICA_SERVER = "replica";

    @Bean(SOURCE_SERVER)
    @ConfigurationProperties("coupon.datasource.writer")
    public DataSource source() {
        return DataSourceBuilder.create()
                .build();
    }

    @Bean(REPLICA_SERVER)
    @ConfigurationProperties("coupon.datasource.reader")
    public DataSource replica() {
        return DataSourceBuilder.create()
                .build();
    }

    @Bean
    public DataSource routingDataSource(
            @Qualifier(SOURCE_SERVER) DataSource source,
            @Qualifier(REPLICA_SERVER) DataSource replica
    ) {
        RoutingDataSource routingDataSource = new RoutingDataSource();
        Map<Object, Object> routingTable = Map.of(
                SOURCE_SERVER, source,
                REPLICA_SERVER, replica
        );
        routingDataSource.setTargetDataSources(routingTable);
        routingDataSource.setDefaultTargetDataSource(source);
        return routingDataSource;
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(routingDataSource(source(), replica()));
    }
}
