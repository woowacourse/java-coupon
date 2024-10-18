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

    @Bean
    @Primary
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(routeDataSource());
    }

    @Bean
    public DataSource routeDataSource() {
        var router = new ReadOnlyDataSourceRouter();
        var writer = writerDataSource();
        var reader = readerDataSource();
        Map<Object, Object> targetDataSources = Map.of(
                DataSourceType.WRITER, writer,
                DataSourceType.READER, reader);
        router.setDefaultTargetDataSource(writer);
        router.setTargetDataSources(targetDataSources);
        return router;
    }

    @Bean
    @ConfigurationProperties("coupon.datasource.writer")
    public DataSource writerDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("coupon.datasource.reader")
    public DataSource readerDataSource() {
        return DataSourceBuilder.create().build();
    }
}
