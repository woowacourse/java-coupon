package coupon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class TestConfig {

    @Autowired
    private DataSource readerDataSource;

    @Autowired
    private DataSource writerDataSource;

    @Bean
    public JdbcTemplate readerJdbcTemplate() {
        return new JdbcTemplate(readerDataSource);
    }

    @Bean
    public JdbcTemplate writerJdbcTemplate() {
        return new JdbcTemplate(writerDataSource);
    }
}
