package coupon;

import static org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@EnableCaching
@Configuration
public class RedisConfig {

    @Bean
    public RedisCacheManager couponCacheManager(RedisConnectionFactory connectionFactory) {
        Map<String, RedisCacheConfiguration> initialCacheConfig = Collections.singletonMap(
                "coupon",
                defaultCacheConfig().entryTtl(Duration.ofSeconds(30L))
        );

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultCacheConfig())
                .transactionAware()
                .withInitialCacheConfigurations(initialCacheConfig)
                .build();
    }
}
