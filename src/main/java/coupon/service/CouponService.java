package coupon.service;

import coupon.domain.coupon.Coupon;
import coupon.repository.CouponRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository, ApplicationContext context) {
        this.couponRepository = couponRepository;
    }

    @Transactional
    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Transactional
    public Coupon getCoupon(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 쿠폰 id입니다."));
    }
}
