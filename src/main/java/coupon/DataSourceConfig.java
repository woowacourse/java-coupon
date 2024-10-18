package coupon;

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

import com.zaxxer.hikari.HikariDataSource;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = "coupon")
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties("coupon.datasource.writer")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @ConfigurationProperties("coupon.datasource.reader")
    public DataSource readDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @DependsOn({"writeDataSource", "readDataSource"})
    public DataSource routeDataSource() {
        ReadOnlyDataSourceRouter readOnlyDataSourceRouter = new ReadOnlyDataSourceRouter();
        DataSource writerDataSource = writeDataSource();
        DataSource readerDataSource = readDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceType.WRITER, writerDataSource);
        dataSourceMap.put(DataSourceType.READER, readerDataSource);

        readOnlyDataSourceRouter.setTargetDataSources(dataSourceMap);
        readOnlyDataSourceRouter.setDefaultTargetDataSource(writerDataSource);

        return readOnlyDataSourceRouter;
    }

    @Bean
    @Primary
    @DependsOn({"routeDataSource"})
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(routeDataSource());
    }
}
