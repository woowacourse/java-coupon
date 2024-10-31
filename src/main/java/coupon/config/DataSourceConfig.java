package coupon.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages = "coupon")
public class DataSourceConfig {

    private static final String WRITER_DATA_SOURCE = "writerDataSource";
    private static final String READER_DATA_SOURCE = "readerDataSource";
    private static final String ROUTING_DATA_SOURCE = "routingDataSource";

    @Bean(name = WRITER_DATA_SOURCE)
    @ConfigurationProperties(prefix = "coupon.datasource.writer")
    public DataSource writerDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = READER_DATA_SOURCE)
    @ConfigurationProperties(prefix = "coupon.datasource.reader")
    public DataSource readerDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = ROUTING_DATA_SOURCE)
    @DependsOn({WRITER_DATA_SOURCE, READER_DATA_SOURCE})
    public DataSource routingDataSource(
            @Qualifier(WRITER_DATA_SOURCE) DataSource writerDataSource,
            @Qualifier(READER_DATA_SOURCE) DataSource readerDataSource
    ) {
        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put(DataSourceType.WRITER, writerDataSource);
        dataSources.put(DataSourceType.READER, readerDataSource);
        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setTargetDataSources(dataSources);
        routingDataSource.setDefaultTargetDataSource(writerDataSource);
        return routingDataSource;
    }

    @Bean
    @Primary
    @DependsOn(ROUTING_DATA_SOURCE)
    public DataSource dataSource(@Qualifier(ROUTING_DATA_SOURCE) DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
}
