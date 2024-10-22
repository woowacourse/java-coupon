package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.repository.CouponRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional(readOnly = true)
@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponLookupService couponLookupService;

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }

    public Coupon getCoupon(Long couponId) {
        return couponLookupService.findById(couponId);
    }
}
