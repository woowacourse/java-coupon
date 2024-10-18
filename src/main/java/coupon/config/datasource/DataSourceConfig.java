package coupon.config.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {

    @Bean(name = "writerDataSource")
    @ConfigurationProperties(prefix = "coupon.datasource.writer")
    public DataSource writerDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = "readerDataSource")
    @ConfigurationProperties(prefix = "coupon.datasource.reader")
    public DataSource readerDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @DependsOn({"writerDataSource", "readerDataSource"})
    @Bean(name = "routingDataSource")
    public DataSource routingDataSource(
            @Qualifier("writerDataSource") DataSource writerDataSource,
            @Qualifier("readerDataSource") DataSource readerDataSource
    ) {
        ReadOnlyDataSourceRouter router = new ReadOnlyDataSourceRouter();

        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put(DataSourceType.WRITER, writerDataSource);
        dataSources.put(DataSourceType.READER, readerDataSource);


        router.setTargetDataSources(dataSources);
        router.setDefaultTargetDataSource(writerDataSource);

        return router;
    }

    @Primary
    @DependsOn("routingDataSource")
    @Bean(name = "dataSource")
    public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
}
