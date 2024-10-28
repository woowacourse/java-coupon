package coupon;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public String getRedis(RedisParam param) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        String result = (String) operations.get(param.getKey());
        if (!StringUtils.hasText(result)) {
            operations.set(param.getKey(), param.getValue(), 10, TimeUnit.MINUTES);
            log.info("redis save");
            result = param.getValue();
        }
        return result;
    }
}
