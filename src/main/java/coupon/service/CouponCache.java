package coupon.service;

import coupon.domain.Coupon;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class CouponCache {

    private final Cache couponCache;

    public CouponCache(CacheManager cacheManager) {
        this.couponCache = cacheManager.getCache("coupon");
        if (couponCache == null) {
            throw new IllegalStateException("쿠폰 캐시를 찾을 수 없습니다.");
        }
    }

    public void cache(Coupon coupon) {
        couponCache.put(coupon.getId(), coupon);
    }

    public Coupon get(Long id) {
        return couponCache.get(id, Coupon.class);
    }

    public boolean contains(Long id) {
        return couponCache.get(id) != null;
    }
}
