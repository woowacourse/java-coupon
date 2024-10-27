package coupon.config;

import com.zaxxer.hikari.HikariDataSource;
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

import static coupon.config.DataSourceRouter.READER_DB_KEY;
import static coupon.config.DataSourceRouter.WRITER_DB_KEY;

@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties("coupon.datasource.reader")
    public DataSource readDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @ConfigurationProperties("coupon.datasource.writer")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @DependsOn({"readDataSource", "writeDataSource"})
    public DataSource routeDataSource() {
        DataSourceRouter router = new DataSourceRouter();

        Map<Object, Object> dataSources = new HashMap<>();
        DataSource reader = readDataSource();
        DataSource writer = writeDataSource();
        dataSources.put(READER_DB_KEY, reader);
        dataSources.put(WRITER_DB_KEY, writer);
        router.setTargetDataSources(dataSources);
        router.setDefaultTargetDataSource(writer);

        return router;
    }

    @Bean
    @Primary
    @DependsOn("routeDataSource")
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(routeDataSource());
    }
}
