package coupon.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Duration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig {

    private static final Duration TTL = Duration.ofDays(1);

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory cf) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
                )
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                                new GenericJackson2JsonRedisSerializer(objectMapper())
                        )
                )
                .entryTtl(TTL);

        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(cf)
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        BasicPolymorphicTypeValidator typeValidator = BasicPolymorphicTypeValidator.builder()
                .allowIfBaseType(Object.class)
                .build();

        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .activateDefaultTyping(typeValidator, DefaultTyping.EVERYTHING, JsonTypeInfo.As.PROPERTY)
                .disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
    }
}
