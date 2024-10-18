package coupon.coupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }

    public Coupon getCoupon(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다. 쿠폰 ID : " + couponId));
    }
}
