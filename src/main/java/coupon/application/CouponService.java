package coupon.application;

import coupon.domain.Coupon;
import coupon.domain.CouponCache;
import coupon.domain.CouponRepository;
import coupon.dto.CouponResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponCache couponCache;

    @Transactional
    public Long create(Coupon coupon) {
        Coupon savedCoupon = couponRepository.save(coupon);
        couponCache.save(savedCoupon);

        return savedCoupon.getId();
    }

    @Transactional(readOnly = true)
    public CouponResponse getCoupon(Long couponId) {
        Coupon coupon = couponCache.findById(couponId)
                .orElseGet(() -> findCoupon(couponId));

        return CouponResponse.from(coupon);
    }

    private Coupon findCoupon(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰이 존재하지 않습니다."));
    }
}
