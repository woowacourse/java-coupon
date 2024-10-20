package coupon.coupon.persistence;

import coupon.coupon.domain.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponReader {

    private final CouponRepository couponRepository;

    public Coupon getCoupon(long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format("%d와 일치하는 쿠폰이 존재하지 않습니다.", couponId))
                );
    }
}
