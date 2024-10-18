package coupon.service;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.Coupon;
import coupon.repository.CouponRepository;

@Service
@Transactional(readOnly = true)
public class CouponService {

    private final CouponRepository couponRepository;
    private final CacheManager cacheManager;

    public CouponService(CouponRepository couponRepository, CacheManager cacheManager) {
        this.couponRepository = couponRepository;
        this.cacheManager = cacheManager;
    }

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
        Cache cache = cacheManager.getCache("coupon");
        cache.put(coupon.getId(), coupon);
    }

    @Cacheable(value = "coupon", key = "#id")
    public Coupon getCoupon(long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰을 찾을 수 없습니다. id = " + id));
    }
}
