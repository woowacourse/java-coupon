package coupon.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import coupon.global.cache.CacheType;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        List<CaffeineCache> caches = caches();
        cacheManager.setCaches(caches);

        return cacheManager;
    }

    private List<CaffeineCache> caches() {
        return Arrays.stream(CacheType.values())
                .map(this::getCaffeineCache)
                .toList();
    }

    private CaffeineCache getCaffeineCache(CacheType cache) {
        return new CaffeineCache(
                cache.getCacheName(),
                Caffeine.newBuilder()
                        .expireAfterWrite(cache.getExpiredAfterWrite(), TimeUnit.MINUTES)
                        .maximumSize(cache.getMaximumSize())
                        .recordStats()
                        .build()
        );
    }
}
