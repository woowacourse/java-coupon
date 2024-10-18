package coupon.support.datasource;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "coupon.datasource.writer")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "coupon.datasource.reader")
    public DataSource readDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @DependsOn({"writeDataSource", "readDataSource"})
    public DataSource routingDataSource() {

        final ReadOnlyDataSourceRouter routingDataSource = new ReadOnlyDataSourceRouter();
        final Map<Object, Object> dataSourceMap = Map.of(
                DataSourceType.READER, readDataSource(),
                DataSourceType.WRITER, writeDataSource());

        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(writeDataSource());

        return routingDataSource;
    }

    @Bean
    @Primary
    @DependsOn({"routingDataSource"})
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(routingDataSource());
    }
}
