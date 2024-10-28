package coupon.config.dataSource;

import java.util.Map;
import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.jpa.properties")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder, DataSource dataSource
    ) {
        return builder.dataSource(dataSource)
                .packages("coupon.entity")
                .build();
    }

    @Primary
    @DependsOn({"readerDataSource", "writerDataSource"})
    @Bean(name = "routingDataSource")
    public DataSource dataSource(DataSource readerDataSource, DataSource writerDataSource) {
        RoutingDataSource routingDataSource = new RoutingDataSource();

        routingDataSource.setTargetDataSources(Map.of(
                DataSourceType.READER, readerDataSource,
                DataSourceType.WRITER, writerDataSource
        ));
        routingDataSource.setDefaultTargetDataSource(writerDataSource);
        routingDataSource.afterPropertiesSet();

        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Bean(name = "readerDataSource")
    @ConfigurationProperties(prefix = "coupon.datasource.reader")
    public DataSource readerDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "writerDataSource")
    @ConfigurationProperties(prefix = "coupon.datasource.writer")
    public DataSource writerDataSource() {
        return DataSourceBuilder.create().build();
    }
}
