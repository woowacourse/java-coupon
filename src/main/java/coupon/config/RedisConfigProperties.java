package coupon.config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.data.redis")
public record RedisConfigProperties(
        String host,
        int port,
        Duration ttl
) {
}
