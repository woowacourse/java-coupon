package coupon.config;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
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
    @Primary
    @DependsOn({"routeDataSource"})
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(routeDataSource());
    }

    @Bean
    @DependsOn({"writerDataSource", "readerDataSource"})
    public DataSource routeDataSource() {
        DataSourceRouter dataSourceRouter = new DataSourceRouter();
        DataSource writerDataSource = writerDataSource();
        DataSource readerDataSource = readerDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("write", writerDataSource);
        dataSourceMap.put("read", readerDataSource);
        dataSourceRouter.setTargetDataSources(dataSourceMap);
        dataSourceRouter.setDefaultTargetDataSource(readerDataSource);

        return dataSourceRouter;
    }

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
}
