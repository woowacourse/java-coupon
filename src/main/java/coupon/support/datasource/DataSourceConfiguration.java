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

    private static final String WRITE_DATASOURCE_PREFIX = "coupon.datasource.writer";
    private static final String READ_DATASOURCE_PREFIX = "coupon.datasource.reader";
    private static final String WRITE_DATASOURCE = "writeDataSource";
    private static final String READ_DATASOURCE = "readDataSource";
    private static final String ROUTING_DATASOURCE = "routingDataSource";

    @Bean
    @ConfigurationProperties(prefix = WRITE_DATASOURCE_PREFIX)
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = READ_DATASOURCE_PREFIX)
    public DataSource readDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @DependsOn({WRITE_DATASOURCE, READ_DATASOURCE})
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
    @DependsOn({ROUTING_DATASOURCE})
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(routingDataSource());
    }
}
