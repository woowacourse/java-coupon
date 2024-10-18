package coupon;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;


@Configuration
public class Config {

    @Bean("writer")
    public HikariDataSource writerDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:33306/coupon?characterEncoding=UTF-8&serverTimezone=UTC");
        config.setUsername("root");
        config.setPassword("root");
        return new HikariDataSource(config);
    }

    @Bean("reader")
    public HikariDataSource readerDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:33307/coupon?characterEncoding=UTF-8&serverTimezone=UTC");
        config.setUsername("root");
        config.setPassword("root");
        return new HikariDataSource(config);
    }

    @Bean("routingDataSource")
    public RoutingDataSource routingDataSource(
            @Qualifier("writer") DataSource writerDataSource,
            @Qualifier("reader") DataSource readerDataSource
    ) {
        HashMap<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceType.WRITER, writerDataSource);
        dataSourceMap.put(DataSourceType.READER, readerDataSource);

        return new RoutingDataSource(dataSourceMap);
    }

    @Bean("lazyDataSource")
    @Primary
    public DataSource lazyDataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

//
//    @Bean("entityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
//            @Qualifier("lazyDataSource") DataSource dataSource
//    ) {
//        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
//        entityManagerFactoryBean.setDataSource(dataSource);
//        entityManagerFactoryBean.setPackagesToScan(""); // 엔티티 패키지
//        entityManagerFactoryBean.setPersistenceProvider(new HibernatePersistenceProvider());
//        entityManagerFactoryBean.setJpaProperties(getJpaProperties());
//        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        return entityManagerFactoryBean;
//    }
//
//    private Properties getJpaProperties() {
//        Properties properties = new Properties();
//        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
//        properties.setProperty("hibernate.show_sql", "true");
//        return properties;
//    }
//
//    @Primary
//    @Bean(name = "transactionManager")
//    public PlatformTransactionManager transactionManager(
//            @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory
//    ) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }

}
