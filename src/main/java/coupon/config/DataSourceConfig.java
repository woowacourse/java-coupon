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

    private static final String WRITER = "writer";
    private static final String READER = "reader";
    private static final String DATASOURCE_ROUTER = "router";

    @Bean(WRITER)
    @ConfigurationProperties(prefix = "coupon.datasource.writer")
    public DataSource writerDataSource() {
        return DataSourceBuilder.create()
                .build();
    }

    @Bean(READER)
    @ConfigurationProperties(prefix = "coupon.datasource.reader")
    public DataSource readerDataSource() {
        return DataSourceBuilder.create()
                .build();
    }

    @Bean(DATASOURCE_ROUTER)
    public DataSource routingDataSource(
            @Qualifier(WRITER) DataSource writer,
            @Qualifier(READER) DataSource reader
    ) {
        RoutingDataSource routingDataSource = new RoutingDataSource();
        Map<Object, Object> dataSourceMap = Map.of(
                DataSourceType.WRITER, writer,
                DataSourceType.READER, reader
        );
        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(writer);
        return routingDataSource;
    }

    @Bean
    @Primary
    public DataSource dataSource(@Qualifier(DATASOURCE_ROUTER) DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
}
