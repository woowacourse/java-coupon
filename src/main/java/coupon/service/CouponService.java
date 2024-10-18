package coupon.service;

import coupon.entity.Coupon;
import coupon.repository.CouponRepository;
import coupon.validator.CouponValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponValidator couponValidator;

    @Transactional
    public Coupon create(Coupon coupon) {
        couponValidator.validate(coupon);

        return couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("coupon not found"));
    }
}
