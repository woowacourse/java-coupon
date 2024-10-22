package coupon.coupon.application;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long couponId) {
        String message = "존재하지 않는 쿠폰입니다. id: %d".formatted(couponId);

        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException(message));
    }
}
