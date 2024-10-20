package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.repository.CouponRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional(readOnly = true)
@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final CacheManager cacheManager;

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
        cacheCoupon(coupon);
    }

    public Coupon getCoupon(Long id) {
        Coupon cachedCoupon = getCachedCoupon(id);
        if (cachedCoupon != null) {
            return cachedCoupon;
        }

        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰이 존재하지 않습니다."));
        cacheCoupon(coupon);
        return coupon;
    }

    private void cacheCoupon(Coupon coupon) {
        getCouponCache().put(coupon.getId(), coupon);
    }

    private Coupon getCachedCoupon(Long id) {
    }

    private Cache getCouponCache() {
        Cache cache = cacheManager.getCache("coupons");
        if (cache == null) {
            throw new IllegalStateException("쿠폰 캐시를 찾을 수 없습니다.");
        }
        return cache;
    }
}
