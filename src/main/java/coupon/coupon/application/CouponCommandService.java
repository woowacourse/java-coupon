package coupon.coupon.application;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponCommandService {

    private final CouponRepository couponRepository;

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }
}
