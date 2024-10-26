package coupon.config;


import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Profile(value = {"prod", "multiDataSourceTest"})
@Configuration
public class DataSourceConfig {

    private static final String WRITER_DATA_SOURCE_BEAN_NAME = "writerDataSource";
    private static final String READER_DATA_SOURCE_BEAN_NAME = "readerDataSource";
    private static final String WRITER_DATA_SOURCE_PREFIX = "coupon.datasource.writer";
    private static final String READER_DATA_SOURCE_PREFIX = "coupon.datasource.reader";
    private static final String ROUTING_DATA_SOURCE = "routingDataSource";

    @Bean(name = WRITER_DATA_SOURCE_BEAN_NAME)
    @ConfigurationProperties(prefix = WRITER_DATA_SOURCE_PREFIX)
    public DataSource writerDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = READER_DATA_SOURCE_BEAN_NAME)
    @ConfigurationProperties(prefix = READER_DATA_SOURCE_PREFIX)
    public DataSource readerDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = ROUTING_DATA_SOURCE)
    public DataSource routingDataSource(
            @Qualifier(WRITER_DATA_SOURCE_BEAN_NAME) DataSource writerDataSourceType,
            @Qualifier(READER_DATA_SOURCE_BEAN_NAME) DataSource readerDataSourceType
    ) {

        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();

        Map<Object, Object> dataSources = new HashMap<>();

        dataSources.put(DataSourceType.WRITER, writerDataSourceType);
        dataSources.put(DataSourceType.READER, readerDataSourceType);

        routingDataSource.setTargetDataSources(dataSources);
        routingDataSource.setDefaultTargetDataSource(writerDataSourceType);

        return routingDataSource;
    }

    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource(@Qualifier(ROUTING_DATA_SOURCE) DataSource routingDataSourceType) {
        return new LazyConnectionDataSourceProxy(routingDataSourceType);
    }
}
