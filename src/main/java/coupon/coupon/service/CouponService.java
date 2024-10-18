package coupon.coupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.repository.CouponRepository;
import coupon.infrastructure.WriterDatabase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @WriterDatabase
    public Coupon getCoupon(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다. 쿠폰 ID : " + couponId));
    }
}
