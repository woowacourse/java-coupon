package coupon.config;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {

    protected static final String WRITER = "writer";
    protected static final String READER = "reader";
    private static final String WRITER_DATA_SOURCE = "writerDataSource";
    private static final String READER_DATA_SOURCE = "readerDataSource";
    private static final String ROUTING_DATA_SOURCE = "routingDataSource";

    @ConfigurationProperties(prefix = "coupon.datasource.reader")
    @Bean(name = READER_DATA_SOURCE)
    public DataSource readerDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @ConfigurationProperties(prefix = "coupon.datasource.writer")
    @Bean(name = WRITER_DATA_SOURCE)
    public DataSource writerDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @DependsOn({WRITER_DATA_SOURCE, READER_DATA_SOURCE})
    @Bean
    public DataSource routingDataSource(
            @Qualifier(WRITER_DATA_SOURCE) DataSource writerDataSource,
            @Qualifier(READER_DATA_SOURCE) DataSource readerDataSource
    ) {
        DynamicRoutingDataSource routingDataSource = new DynamicRoutingDataSource();
        Map<Object, Object> dataSourceMap = createDataSourceMap(writerDataSource, readerDataSource);
        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(writerDataSource);

        return routingDataSource;
    }

    private Map<Object, Object> createDataSourceMap(DataSource writer, DataSource reader) {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(WRITER, writer);
        dataSourceMap.put(READER, reader);
        return dataSourceMap;
    }

    @DependsOn({ROUTING_DATA_SOURCE})
    @Primary
    @Bean
    public DataSource dataSource(@Qualifier(ROUTING_DATA_SOURCE) DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }
}
