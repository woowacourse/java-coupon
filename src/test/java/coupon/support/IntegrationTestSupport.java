package coupon.support;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootTest(classes = {IntegrationTestSupport.TestConfig.class})
@ExtendWith(TestClearExtension.class)
public abstract class IntegrationTestSupport {

    @TestConfiguration
    public static class TestConfig {

        @Bean
        public TestCleaner databaseCleaner() {
            return new TestCleaner();
        }
    }
}
