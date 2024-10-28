package coupon.support;

import java.time.Duration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class WriteTimeChecker {

    private final RedisTemplate<String, Boolean> template;

    public WriteTimeChecker(RedisTemplate<String, Boolean> template) {
        this.template = template;
    }

    public boolean isAvailableToRead(String key) {
        // 키가 존재하면 Read DB로 가면 안 된다.
        return Boolean.FALSE.equals(template.hasKey(key));
    }

    public void renewKey(String key, Duration timeout) {
        ValueOperations<String, Boolean> valueOperations = template.opsForValue();
        valueOperations.set(key, true, timeout);
    }
}
