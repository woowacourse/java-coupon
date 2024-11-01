package coupon.service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class LRUCouponCache<Long, Coupon> implements Cache<Long, Coupon> {

    private static final float CACHE_LOAD_THRESHOLD = 0.75f;

    private final Map<Long, Coupon> cache;

    public LRUCouponCache(int cacheSize) {
        this.cache = Collections.synchronizedMap(new LinkedHashMap<>(cacheSize, CACHE_LOAD_THRESHOLD, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Long, Coupon> eldest) {
                return size() > cacheSize;
            }
        });
    }

    @Override
    public Optional<Coupon> get(Long key) {
        Coupon coupon = cache.get(key);
        return Optional.ofNullable(coupon);
    }

    @Override
    public void put(Long key, Coupon value) {
        cache.put(key, value);
    }

    @Override
    public void remove(Long key) {
        cache.remove(key);
    }
}
