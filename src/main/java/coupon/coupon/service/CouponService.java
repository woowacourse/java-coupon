package coupon.coupon.service;

import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;
import coupon.coupon.repository.CouponRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Transactional
    public Long createCoupon() {
        Coupon newCoupon = new Coupon("쿠폰", new BigDecimal(1000), new BigDecimal(10000),
                Category.FOOD, LocalDateTime.now(), LocalDateTime.now().plusWeeks(1L));
        Coupon coupon = couponRepository.save(newCoupon);

        return coupon.getId();
    }

    @Transactional(readOnly = true)
    public Coupon findCouponById(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰이 존재하지 않습니다."));
    }

    @Transactional
    public Coupon findCouponByIdWithNoLag(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰이 존재하지 않습니다."));
    }
}
