package coupon.coupon.persistence;

import coupon.coupon.domain.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class CouponWriter {

    private final CouponRepository couponRepository;

    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }
}
