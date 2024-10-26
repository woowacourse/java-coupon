package coupon.config;

import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
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
    public DataSource dataSource(@Qualifier("routeDataSource") DataSource replicationRouteDataSource) {
        return new LazyConnectionDataSourceProxy(replicationRouteDataSource);
    }

    @Bean
    @Qualifier("routeDataSource")
    public DataSource routeDataSource(
            @Qualifier("writeDataSource") DataSource writeDataSource,
            @Qualifier("readDataSource") DataSource readDataSource
    ) {
        HashMap<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceType.WRITER, writeDataSource);
        dataSourceMap.put(DataSourceType.READER, readDataSource);

        ReadOnlyDataSourceRouter readOnlyDataSourceRouter = new ReadOnlyDataSourceRouter();
        readOnlyDataSourceRouter.setTargetDataSources(dataSourceMap);
        readOnlyDataSourceRouter.setDefaultTargetDataSource(writeDataSource);
        return readOnlyDataSourceRouter;
    }
}
