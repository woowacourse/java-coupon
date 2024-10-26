package coupon.config.db;

import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class ReaderDataSourceConfig {

    @ConfigurationProperties(prefix = DataSourceConstants.READER_DATA_SOURCE_PREFIX)
    @Bean(name = DataSourceConstants.READER_DATA_SOURCE)
    public DataSource readerDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
}
