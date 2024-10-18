package coupon;

import javax.sql.DataSource;
import java.util.HashMap;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Configuration
class DataSourceConfig {

    public static final String WRITE_DATASOURCE_NAME = "couponSourceDatasource";
    public static final String READ_DATASOURCE_NAME = "couponReplicaDataSource";
    public static final String ROUTING_DATASOURCE_NAME = "couponRoutingDataSource";

    @Bean
    @Primary
    public DataSource routingLazyDataSource(@Qualifier(ROUTING_DATASOURCE_NAME) DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Bean(name = ROUTING_DATASOURCE_NAME)
    public DataSource routingDataSource(
            @Qualifier(WRITE_DATASOURCE_NAME) DataSource writeDataSource,
            @Qualifier(READ_DATASOURCE_NAME) DataSource readDataSource
    ) {
        RoutingDataSource routingDataSource = new RoutingDataSource();
        HashMap<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(WRITE_DATASOURCE_NAME, writeDataSource);
        dataSourceMap.put(READ_DATASOURCE_NAME, readDataSource);
        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(writeDataSource);

        return routingDataSource;
    }

    @Bean(name = WRITE_DATASOURCE_NAME)
    @ConfigurationProperties(prefix = "coupon.datasource.writer")
    public DataSource sourceDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = READ_DATASOURCE_NAME)
    @ConfigurationProperties(prefix = "coupon.datasource.reader")
    public DataSource replicaDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }
}
