package coupon.support;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "write-time-checker.redis")
public record WriteTimeCheckerRedisProperties(
        String host,
        int port
) {
}
