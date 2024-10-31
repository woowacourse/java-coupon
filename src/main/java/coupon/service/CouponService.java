package coupon.service;

import coupon.aspect.ImmediateRead;
import coupon.domain.Coupon;
import coupon.exception.CouponException;
import coupon.repository.CouponRepository;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponService {

    public static final String COUPON_CACHE_KEY = "coupons";

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = COUPON_CACHE_KEY, key = "#couponId", sync = true)
    public Coupon getCoupon(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponException("쿠폰이 존재하지 않습니다."));
    }

    @ImmediateRead
    @Transactional(readOnly = true)
    public Coupon getCouponImmediately(Long couponId) {
        return getCoupon(couponId);
    }

    @Transactional
    @CachePut(value = COUPON_CACHE_KEY, key = "#coupon.id")
    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }
}
