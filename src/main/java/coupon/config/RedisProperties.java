package coupon.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

@Profile("prod")
@ConfigurationProperties(prefix = "spring.data.redis")
public record RedisProperties(String port, String host) {

}
