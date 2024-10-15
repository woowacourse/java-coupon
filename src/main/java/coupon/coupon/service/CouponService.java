package coupon.coupon.service;

import coupon.coupon.CouponNotFoundException;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Transactional
    public Coupon getCoupon(long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException(couponId));
    }
}
