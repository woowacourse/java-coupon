package coupon.config.redis;

import java.time.Duration;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.data.redis")
@RequiredArgsConstructor
@Getter
public class RedisProperties {

    private final String host;
    private final int port;
    private final Map<String, Integer> ttl;

    public Duration getTtl(String key) {
        return Duration.ofSeconds(ttl.get(key));
    }
}
