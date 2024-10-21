package coupon.common.infra.datasource;

import javax.sql.DataSource;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Configuration
public class DataSourceConfig {

    private static final String WRITER_DATASOURCE_PROPERTIES_LOCATION = "coupon.datasource.writer";
    private static final String READER_DATASOURCE_PROPERTIES_LOCATION = "coupon.datasource.reader";
    private static final String ROUTING_DATASOURCE = "routingDataSource";
    private static final String WRITER_DATA_SOURCE = "writerDataSource";
    private static final String READER_DATA_SOURCE = "readerDataSource";

    @Bean(WRITER_DATA_SOURCE)
    @ConfigurationProperties(WRITER_DATASOURCE_PROPERTIES_LOCATION)
    public DataSource writerDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(READER_DATA_SOURCE)
    @ConfigurationProperties(READER_DATASOURCE_PROPERTIES_LOCATION)
    public DataSource readerDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(ROUTING_DATASOURCE)
    public DataSource routingDataSource(
            @Qualifier(WRITER_DATA_SOURCE) DataSource writerDataSource,
            @Qualifier(READER_DATA_SOURCE) DataSource readerDataSource
    ) {
        RoutingDataSource routingDataSource = new RoutingDataSource();
        Map<Object, Object> dataSources = Map.of(
                DataSourceType.WRITER, writerDataSource,
                DataSourceType.READER, readerDataSource
        );

        routingDataSource.setDefaultTargetDataSource(writerDataSource);
        routingDataSource.setTargetDataSources(dataSources);

        return routingDataSource;
    }

    @Bean
    @Primary
    public DataSource dataSource(@Qualifier(ROUTING_DATASOURCE) DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
}
