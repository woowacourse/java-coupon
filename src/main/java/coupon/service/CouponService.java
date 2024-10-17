package coupon.service;

import coupon.repository.CouponRepository;
import coupon.repository.entity.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public void saveCoupon(Coupon coupon) {
        couponRepository.save(coupon);
    }

    public Coupon getCoupon(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다: %d".formatted(couponId)));
    }
}
