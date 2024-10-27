package coupon.config;

import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    private static final String READ_DATASOURCE = "readerDataSource";
    private static final String WRITE_DATASOURCE = "writerDataSource";
    private static final String ROUTE_DATASOURCE = "routerDataSource";

    @Value( "${spring.datasource.writer.jdbcUrl}")
    private String writerUrl;

    @Value( "${spring.datasource.reader.jdbcUrl}")
    private String readerUrl;

    @Bean
    @Primary
    @DependsOn(ROUTE_DATASOURCE)
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(routingDataSource());
    }

    @Bean(name = ROUTE_DATASOURCE)
    @DependsOn({READ_DATASOURCE, WRITE_DATASOURCE})
    public DataSource routingDataSource() {
        DataSourceRouter dataSourceRouter = new DataSourceRouter();
        DataSource writerDataSource = writerDataSource();
        DataSource readerDataSource = readerDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceType.WRITER, writerDataSource);
        dataSourceMap.put(DataSourceType.READER, readerDataSource);

        dataSourceRouter.setTargetDataSources(dataSourceMap);
        dataSourceRouter.setDefaultTargetDataSource(writerDataSource);
        return dataSourceRouter;
    }

    @Bean(name = WRITE_DATASOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.writer")
    public DataSource writerDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = READ_DATASOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.reader")
    public DataSource readerDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }
}
