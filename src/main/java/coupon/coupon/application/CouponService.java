package coupon.coupon.application;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private static final String COUPON_CACHE_NAME = "coupons";

    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;

    @Transactional(readOnly = true)
    @Cacheable(value = COUPON_CACHE_NAME, key = "#couponId")
    public CouponResponse getCoupon(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("Coupon not found: " + couponId));

        return couponMapper.toResponse(coupon);
    }

    @Transactional
    @CachePut(value = COUPON_CACHE_NAME, key = "#result.id")
    public CouponResponse create(CreateCouponRequest request) {
        Coupon coupon = couponMapper.toCoupon(request);
        Coupon savedCoupon = couponRepository.save(coupon);

        return couponMapper.toResponse(savedCoupon);
    }
}
