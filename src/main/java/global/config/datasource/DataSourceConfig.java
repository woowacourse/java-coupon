package global.config.datasource;

import java.util.HashMap;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Configuration
public class DataSourceConfig {

    public static final String WRITER = "writer";
    public static final String READER = "reader";

    @ConfigurationProperties("coupon.datasource.writer")
    @Bean
    public DataSource writerDataSource() {
        return DataSourceBuilder.create().build();
    }

    @ConfigurationProperties("coupon.datasource.reader")
    @Bean
    public DataSource readerDataSource() {
        return DataSourceBuilder.create().build();
    }

    @DependsOn({"writerDataSource", "readerDataSource"})
    @Bean
    public DataSource routingDataSource(
            @Qualifier("writerDataSource") final DataSource writer,
            @Qualifier("readerDataSource") final DataSource reader
    ) {
        final var routingDataSource = new DynamicRoutingDataSource();
        final var dataSourceMap = new HashMap<>();

        dataSourceMap.put(WRITER, writer);
        dataSourceMap.put(READER, reader);
        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(writer);

        return routingDataSource;
    }

    @DependsOn({"routingDataSource"})
    @Primary
    @Bean
    public DataSource dataSource(DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
}
