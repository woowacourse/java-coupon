package coupon.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
public class DataSourceConfig {

    @Bean(name = "writerDataSource")
    @ConfigurationProperties("coupon.datasource.writer")
    public DataSource writerDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "readerDataSource")
    @ConfigurationProperties("coupon.datasource.reader")
    public DataSource readerDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "routingDataSource")
    @Primary
    public DataSource routingDataSource(
        @Qualifier("writerDataSource") DataSource writerDataSource,
        @Qualifier("readerDataSource") DataSource readerDataSource) {
        ReadOnlyDataSourceRouter routingDataSource = new ReadOnlyDataSourceRouter();

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.READER, readerDataSource);
        targetDataSources.put(DataSourceType.WRITER, writerDataSource);

        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(writerDataSource);
        return routingDataSource;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.jpa.properties")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
        EntityManagerFactoryBuilder builder, @Qualifier("routingDataSource") DataSource dataSource
    ) {
        return builder.dataSource(dataSource)
            .packages("coupon.domain")
            .build();
    }
}
