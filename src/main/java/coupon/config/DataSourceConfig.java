package coupon.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "coupon.datasource.writer")
    public DataSource writerDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "coupon.datasource.reader")
    public DataSource readerDataSource() {
        return DataSourceBuilder.create().build();
    }

    @DependsOn({"writerDataSource", "readerDataSource"})
    @Bean
    public DataSource routeDataSource(
            @Qualifier("writerDataSource") DataSource writerDataSource,
            @Qualifier("readerDataSource") DataSource readerDataSource) {
        ReadOnlyDataSourceRouter readOnlyDataSourceRouter = new ReadOnlyDataSourceRouter();
        Map<Object, Object> dataSources = new HashMap<>();

        dataSources.put(DataSourceType.WRITER, writerDataSource);
        dataSources.put(DataSourceType.READER, readerDataSource);

        readOnlyDataSourceRouter.setTargetDataSources(dataSources);
        readOnlyDataSourceRouter.setDefaultTargetDataSource(writerDataSource);
        return readOnlyDataSourceRouter;
    }

    @DependsOn({"routeDataSource"})
    @Primary
    @Bean
    public DataSource dataSource(DataSource routeDataSource) {
        return new LazyConnectionDataSourceProxy(routeDataSource);
    }
}
