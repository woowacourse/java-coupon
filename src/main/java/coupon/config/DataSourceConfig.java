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

    @Bean
    @Primary
    public DataSource dataSource() {
        DataSource determinedDataSource = routingDataSource(writerDataSource(), readerDataSource());
        return new LazyConnectionDataSourceProxy(determinedDataSource);
    }

    @Bean
    @Qualifier(RoutingDataSource.WRITER)
    @ConfigurationProperties("coupon.datasource.writer")
    public DataSource writerDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Qualifier(RoutingDataSource.READER)
    @ConfigurationProperties("coupon.datasource.reader")
    public DataSource readerDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public DataSource routingDataSource(
            @Qualifier(RoutingDataSource.WRITER) DataSource writerDataSource,
            @Qualifier(RoutingDataSource.READER) DataSource readerDataSource
    ) {
        RoutingDataSource routingDataSource = new RoutingDataSource();

        Map<Object, Object> dataSourceMap = Map.of(
                RoutingDataSource.WRITER, writerDataSource,
                RoutingDataSource.READER, readerDataSource
        );

        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(writerDataSource);

        return routingDataSource;
    }
}
