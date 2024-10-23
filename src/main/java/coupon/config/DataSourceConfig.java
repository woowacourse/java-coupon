package coupon.config;

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

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(basePackages = "coupon")
public class DataSourceConfig {

    private static final String WRITER_DATA_SOURCE = "writeDataSource";
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
    public DataSource routingDataSource() {
        final DataSource writerDataSource = writerDataSource();
        final DataSource readerDataSource = readerDataSource();
        final Map<Object, Object> dataSources = Map.of(
                DataSourceType.WRITER, writerDataSource,
                DataSourceType.READER, readerDataSource
        );
        final ReadOnlyDataSourceRouter readOnlyDataSourceRouter = new ReadOnlyDataSourceRouter();
        readOnlyDataSourceRouter.setTargetDataSources(dataSources);
        readOnlyDataSourceRouter.setDefaultTargetDataSource(writerDataSource);
        return readOnlyDataSourceRouter;
    }

    @Bean
    @Primary
    @DependsOn(ROUTING_DATA_SOURCE)
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(routingDataSource());
    }
}
