package coupon.global;

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

@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "coupon.datasource.writer")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @ConfigurationProperties(prefix = "coupon.datasource.reader")
    public DataSource readDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @Primary
    @DependsOn({"writeDataSource", "readDataSource"})
    public DataSource dataSource() {
        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put(DataSourceType.WRITER, writeDataSource());
        dataSources.put(DataSourceType.READER, readDataSource());

        DataSourceRouter dataSourceRouter = new DataSourceRouter();
        dataSourceRouter.setTargetDataSources(dataSources);
        dataSourceRouter.setDefaultTargetDataSource(writeDataSource());

        return new LazyConnectionDataSourceProxy(dataSourceRouter);
    }
}
