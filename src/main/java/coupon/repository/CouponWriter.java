package coupon.repository;

import coupon.domain.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Transactional
@RequiredArgsConstructor
@Component
public class CouponWriter {
    private final CouponIssuer couponIssuer;
    private final CouponRepository couponRepository;
    private final CouponCacheRepository couponCacheRepository;

    public Coupon create(final Coupon coupon) {
        final Coupon savedCoupon = couponRepository.save(couponIssuer.issueCoupon(coupon));
        couponCacheRepository.save(savedCoupon);
        return savedCoupon;
    }
}
