package coupon.service;

import coupon.domain.Coupon;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponWriteService couponWriteService;
    private final CouponReadService couponReadService;
    private final CouponRepository couponRepository;

    @Transactional
    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Cacheable(value = "coupons", key = "#couponId")
    public Coupon getCoupon(Long couponId) {
        return couponReadService.getCoupon(couponId)
                .orElseGet(() -> couponWriteService.getCoupon(couponId));
    }
}
