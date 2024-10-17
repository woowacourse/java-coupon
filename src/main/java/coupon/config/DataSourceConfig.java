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

    protected static final String WRITER = "writer";
    protected static final String READER = "reader";

    @Bean(name = WRITER)
    @ConfigurationProperties(prefix = "coupon.datasource.writer")
    public DataSource writerDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = READER)
    @ConfigurationProperties(prefix = "coupon.datasource.reader")
    public DataSource readerDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public DataSource dynamicDataSource(
            @Qualifier(WRITER) DataSource writerDataSource,
            @Qualifier(READER) DataSource readerDataSource
    ) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> dataSourceMap = Map.of(
                WRITER, writerDataSource,
                READER, readerDataSource
        );

        dynamicDataSource.setTargetDataSources(dataSourceMap);
        dynamicDataSource.setDefaultTargetDataSource(writerDataSource);
        return dynamicDataSource;
    }

    @Primary
    @Bean
    public DataSource dataSource() {
        DataSource dataSource = dynamicDataSource(writerDataSource(), readerDataSource());
        return new LazyConnectionDataSourceProxy(dataSource);
    }
}
