package coupon.datasource.config;

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

@Configuration
public class DataSourceConfig {

    @Bean
    @Qualifier("writerDataSource")
    @ConfigurationProperties("coupon.datasource.writer")
    public DataSource writerDataSource() {
        return DataSourceBuilder.create()
                .build();
    }

    @Bean
    @Qualifier("readerDataSource")
    @ConfigurationProperties("coupon.datasource.reader")
    public DataSource readerDataSource() {
        return DataSourceBuilder.create()
                .build();
    }

    @Bean
    @DependsOn({"writerDataSource", "readerDataSource"})
    public DataSource routingDataSource(
            @Qualifier("writerDataSource") DataSource writer,
            @Qualifier("readerDataSource") DataSource reader
    ) {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("writer", writer);
        dataSourceMap.put("reader", reader);

        DynamicRoutingDataSource routingDataSource = new DynamicRoutingDataSource();
        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(writer);
        return routingDataSource;
    }

    @Bean
    @Primary
    @DependsOn({"routingDataSource"})
    public DataSource dataSource(DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
}
