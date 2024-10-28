package coupon.helper;

import java.util.function.Supplier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CacheExecutor {

    @Cacheable(value = "defaultCache", key = "#key")
    public <T> T executeWithCache(Object key, Supplier<T> supplier) {
        return supplier.get();
    }
}
