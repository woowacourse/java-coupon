package coupon.support;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
@EnableConfigurationProperties(WriteTimeCheckerRedisProperties.class)
@RequiredArgsConstructor
public class WriteTimeCheckerRedisConfig {

    private final WriteTimeCheckerRedisProperties properties;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(properties.host(), properties.port());
    }

    @Bean
    public RedisTemplate<String, Boolean> redisTemplate() {
        RedisTemplate<String, Boolean> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setValueSerializer(new GenericToStringSerializer<>(Boolean.class));
        return redisTemplate;
    }
}
