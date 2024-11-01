package coupon.config.cache.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
                .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(getRedisObjectMapper()))); //값은 json으로 직렬화
    }

    private ObjectMapper getRedisObjectMapper() {
            ObjectMapper objectMapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule()) //LocalDateTime 호환을 위한 모듈 추가
                    .setVisibility(PropertyAccessor.ALL, Visibility.NONE)
                    .setVisibility(PropertyAccessor.FIELD, Visibility.ANY) //1순위 : field로 직렬/역직렬화 하기
                    .setVisibility(PropertyAccessor.CREATOR, Visibility.PUBLIC_ONLY) //2순위 : 생성자
                    .configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, true); //자기 참조 시 무한루프 방지 (default 설정)

            //직렬화 시 type 정보를 저장할 scope 지정
            objectMapper.activateDefaultTyping(
                    BasicPolymorphicTypeValidator.builder().allowIfBaseType(Object.class).build(), //커스텀 objectmapper에게 클래스 정보도 함께 저장
                    DefaultTyping.NON_CONCRETE_AND_ARRAYS
            );

            return objectMapper;
    }
}
