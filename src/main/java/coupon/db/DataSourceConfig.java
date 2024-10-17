package coupon.db;

import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

public class DataSourceConfig {

    @Bean
    @Qualifier("writeDataSource")
    @ConfigurationProperties("coupon.datasource.writer")
    DataSource writeDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @Qualifier("readDataSource")
    @ConfigurationProperties("coupon.datasource.reader")
    DataSource readDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    Map<Object, Object> dataSourceMap(
            @Qualifier("writeDataSource") DataSource writeDataSource,
            @Qualifier("readDataSource") DataSource readeDataSource
    ) {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceType.WRITER, writeDataSource);
        dataSourceMap.put(DataSourceType.READER, readeDataSource);
        return dataSourceMap;
    }

    @Bean
    @Qualifier("routeDataSource")
    DataSource routeDataSource(Map<Object, Object> dataSourceMap) {
        DataSourceRouter dataSourceRouter = new DataSourceRouter();
        dataSourceRouter.setTargetDataSources(dataSourceMap);
        dataSourceRouter.setDefaultTargetDataSource(dataSourceMap.get(DataSourceType.READER));
        return dataSourceRouter;
    }

    @Bean
    @Primary
    DataSource dataSource(@Qualifier("routeDataSource") DataSource dataSource) {
        return new LazyConnectionDataSourceProxy(dataSource);
    }
}
