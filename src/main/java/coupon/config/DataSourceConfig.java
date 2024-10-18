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
    @ConfigurationProperties("coupon.datasource.writer")
    public DataSource writerDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @ConfigurationProperties("coupon.datasource.reader")
    public DataSource readerDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @Primary
    public DataSource dataSource(@Qualifier("routeDataSource") DataSource routeDataSource) {
        return new LazyConnectionDataSourceProxy(routeDataSource);
    }

    @Bean
    public DataSource routeDataSource(
            @Qualifier("writerDataSource") DataSource writerDataSource,
            @Qualifier("readerDataSource") DataSource readerDataSource
    ) {
        HashMap<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceType.WRITER, writerDataSource);
        dataSourceMap.put(DataSourceType.READER, readerDataSource);

        ReadOnlyDataSourceRouter readOnlyDataSourceRouter = new ReadOnlyDataSourceRouter();
        readOnlyDataSourceRouter.setTargetDataSources(dataSourceMap);
        readOnlyDataSourceRouter.setDefaultTargetDataSource(writerDataSource);
        return readOnlyDataSourceRouter;
    }
}
