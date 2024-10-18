package coupon.datasource;

import com.zaxxer.hikari.HikariDataSource;
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

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
public class DatasourceConfig {
    // Write replica 정보로 만든 DataSource
    @Bean
    @ConfigurationProperties(prefix = "coupon.datasource.writer")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    // Read replica 정보로 만든 DataSource
    @Bean
    @ConfigurationProperties(prefix = "coupon.datasource.reader")
    public DataSource readDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    @DependsOn({"writeDataSource", "readDataSource"})
    public DataSource routeDataSource(@Qualifier("writeDataSource") final DataSource writeDataSource, @Qualifier("readDataSource") final DataSource readDataSource) {
        final DataSourceRouter dataSourceRouter = new DataSourceRouter();
        final HashMap<Object, Object> dataSourceMap = new HashMap<>();

        dataSourceMap.put(DataSourceType.WRITER, writeDataSource);
        dataSourceMap.put(DataSourceType.READER, readDataSource);

        dataSourceRouter.setTargetDataSources(dataSourceMap);
        dataSourceRouter.setDefaultTargetDataSource(writeDataSource);
        return dataSourceRouter;
    }

    @DependsOn({"routeDataSource"})
    @Primary
    @Bean
    public DataSource dataSource(final DataSource routeDataSource) {
        return new LazyConnectionDataSourceProxy(routeDataSource);
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
