package coupon.config;

import java.util.HashMap;
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

    @Bean(name = "writerDataSource")
    @ConfigurationProperties(prefix = "coupon.datasource.writer")
    public DataSource writerDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "readerDataSource")
    @ConfigurationProperties(prefix = "coupon.datasource.reader")
    public DataSource readerDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(routingDataSource());  // 커넥션을 지연시킴
    }

    @Bean
    public DataSource routingDataSource() {
        ReadOnlyDataSourceRouter router = new ReadOnlyDataSourceRouter();
        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put(DataSourceType.WRITER, writerDataSource());
        dataSources.put(DataSourceType.READER, readerDataSource());
        router.setTargetDataSources(dataSources);
        router.setDefaultTargetDataSource(writerDataSource());  // 기본 데이터소스 설정
        return router;
    }
}
