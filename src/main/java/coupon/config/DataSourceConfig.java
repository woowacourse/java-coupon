package coupon.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
        basePackages = "coupon.repository",
        entityManagerFactoryRef = "couponEntityManagerFactory",
        transactionManagerRef = "couponTransactionManager"
)
public class DataSourceConfig {

    @Bean(name = "writerDataSource")
    @ConfigurationProperties(prefix = "coupon.datasource.writer")
    public DataSource writerDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "readerDataSource")
    @ConfigurationProperties(prefix = "coupon.datasource.reader")
    public DataSource readerDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "routingDataSource")
    public DataSourceRouter routingDataSource(
            @Qualifier("writerDataSource") DataSource writerDataSource,
            @Qualifier("readerDataSource") DataSource readerDataSource) {
        DataSourceRouter router = new DataSourceRouter();
        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put(DataSourceType.WRITER, writerDataSource);
        dataSources.put(DataSourceType.READER, readerDataSource);
        router.setTargetDataSources(dataSources);
        router.setDefaultTargetDataSource(writerDataSource);
        return router;
    }

    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource(
            @Qualifier("routingDataSource") DataSourceRouter routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Bean(name = "couponEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean couponEntityManagerFactory(
            @Qualifier("dataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("coupon.entity");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.MYSQL);
        em.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.physical_naming_strategy",
                "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
        properties.put("hibernate.implicit_naming_strategy",
                "org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl");
        properties.put("hibernate.show_sql", "true");
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean
    public PlatformTransactionManager couponTransactionManager(
            @Qualifier("couponEntityManagerFactory") LocalContainerEntityManagerFactoryBean couponEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(couponEntityManagerFactory.getObject()));
    }
}
