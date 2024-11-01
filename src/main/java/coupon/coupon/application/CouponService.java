package coupon.coupon.application;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CouponService {

    private static final String CACHE_NAME = "coupon";

    private final CouponRepository couponRepository;

    @CachePut(value = CACHE_NAME, key = "#result.id")
    public Coupon create(final Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Cacheable(value = CACHE_NAME, key = "#id", unless = "#result == null")
    public Coupon getCoupon(final Long id) {
        return couponRepository.fetchById(id);
    }
}
