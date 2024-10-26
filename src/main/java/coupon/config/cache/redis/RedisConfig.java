package coupon.config.cache.redis;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory, RedisCacheConfiguration config) {
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .transactionAware() //트랜잭션이 적용된 메소드에서 cache의 commit/rollback 동기화
                .build();
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        //spring의 default redis client : 비동기 이벤트 기반이라 jedis에 비해 고성능
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(
                redisProperties.getHost(),
                redisProperties.getPort())
        );
    }

    @Bean
    public RedisCacheConfiguration redisConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10L))
                .disableCachingNullValues()
                .serializeKeysWith(SerializationPair.fromSerializer(new StringRedisSerializer())) //키는 string으로 직렬화
                .serializeValuesWith(
                        SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())); //값은 json으로 직렬화
    }
}
