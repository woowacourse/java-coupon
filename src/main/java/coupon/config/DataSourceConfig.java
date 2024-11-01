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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Configuration
@EnableJpaRepositories(basePackages = {"coupon.coupon.repository", "coupon.member.repository"})
public class DataSourceConfig {

    private static final String WRITER_DATA_SOURCE = "writerDataSource";
    private static final String READER_DATA_SOURCE = "readerDataSource";
    private static final String ROUTE_DATA_SOURCE = "routeDataSource";

    @Bean(name = WRITER_DATA_SOURCE)
    @ConfigurationProperties(prefix = "coupon.datasource.writer")
    public DataSource writerDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = READER_DATA_SOURCE)
    @ConfigurationProperties(prefix = "coupon.datasource.reader")
    public DataSource readerDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = ROUTE_DATA_SOURCE)
    @DependsOn({WRITER_DATA_SOURCE, READER_DATA_SOURCE})
    public ReadOnlyDataSourceRouter routeDataSource() {
        ReadOnlyDataSourceRouter dataSourceRouter = new ReadOnlyDataSourceRouter();
        DataSource writeDataSource = writerDataSource();
        DataSource readDataSource = readerDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceType.READER, readDataSource);
        dataSourceMap.put(DataSourceType.WRITER, writeDataSource);

        dataSourceRouter.setTargetDataSources(dataSourceMap);
        dataSourceRouter.setDefaultTargetDataSource(writerDataSource());
        return dataSourceRouter;
    }

    @Bean
    @Primary
    @DependsOn(ROUTE_DATA_SOURCE)
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(routeDataSource());
    }
}
