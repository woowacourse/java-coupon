package coupon.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    public static final long CACHE_DURATION_DAYS = 1L;

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        final RedisCacheConfiguration configuration = getRedisCacheConfiguration();

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(configuration)
                .build();
    }

    private RedisCacheConfiguration getRedisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(SerializationPair.fromSerializer(getValueSerializer()))
                .entryTtl(Duration.ofDays(CACHE_DURATION_DAYS));
    }

    private GenericJackson2JsonRedisSerializer getValueSerializer() {
        PolymorphicTypeValidator typeValidator = getPolymorphicTypeValidator();
        ObjectMapper objectMapper = getObjectMapper(typeValidator);

        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

    private PolymorphicTypeValidator getPolymorphicTypeValidator() {
        return BasicPolymorphicTypeValidator.builder()
                .allowIfSubType(Object.class)
                .build();
    }

    private ObjectMapper getObjectMapper(PolymorphicTypeValidator typeValidator) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.activateDefaultTyping(typeValidator, DefaultTyping.EVERYTHING);

        return objectMapper;
    }
}
