package coupon.coupon.support;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

@TestComponent
public class CacheCleaner {

    @Autowired
    private CacheManager cacheManager;

    public void execute() {
        cacheManager.getCacheNames()
                .stream()
                .map(cacheManager::getCache)
                .filter(Objects::nonNull)
                .forEach(Cache::clear);
    }
}
