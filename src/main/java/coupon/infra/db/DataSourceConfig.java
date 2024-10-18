package coupon.infra.db;

import com.zaxxer.hikari.HikariDataSource;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Configuration
@EnableJpaRepositories(basePackages = "coupon.infra.db.jpa")
@EnableRedisRepositories(basePackages = "coupon.infra.db.redis")
class DataSourceConfig {

    static final String WRITE = "write";
    static final String READ = "read";

    @Bean
    @ConfigurationProperties(prefix = "coupon.datasource.writer")
    DataSource writeDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @ConfigurationProperties(prefix = "coupon.datasource.reader")
    DataSource readDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    DataSource routeDataSource() {
        DataSourceRouter dataSourceRouter = new DataSourceRouter();
        DataSource writeDataSource = writeDataSource();
        DataSource readDataSource = readDataSource();

        Map<Object, Object> dataSourceMap = Map.of(
                WRITE, writeDataSource,
                READ, readDataSource
        );
        dataSourceRouter.setTargetDataSources(dataSourceMap);
        dataSourceRouter.setDefaultTargetDataSource(writeDataSource);

        return dataSourceRouter;
    }

    @Bean
    @Primary
    DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(routeDataSource());
    }
}
