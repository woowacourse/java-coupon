package coupon.coupon.support;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestConfig {

    @Bean
    public DatabaseCleaner databaseCleaner() {
        return new DatabaseCleaner();
    }

    @Bean
    public CacheCleaner cacheCleaner() {
        return new CacheCleaner();
    }

    @Bean
    @Primary
    public Clock testClock() {
        return Clock.fixed(Instant.parse("2024-01-01T00:00:00Z"), ZoneId.of("UTC+9"));
    }
}
