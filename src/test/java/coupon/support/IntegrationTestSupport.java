package coupon.support;

import coupon.support.cleaner.DatabaseCleaner;
import coupon.support.cleaner.DatabaseClearExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootTest(classes = {IntegrationTestSupport.TestConfig.class})
@ExtendWith(DatabaseClearExtension.class)
public abstract class IntegrationTestSupport {

    @TestConfiguration
    public static class TestConfig {

        @Bean
        public DatabaseCleaner databaseCleaner() {
            return new DatabaseCleaner();
        }
    }
}
