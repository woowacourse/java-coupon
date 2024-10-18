package coupon.coupon.business;

import coupon.coupon.domain.Coupon;
import coupon.coupon.infrastructure.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponService {

    private final CouponRepository couponRepository;

    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }

    public Coupon getCoupon(long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 쿠폰 조회 요청입니다. id = " + id));
    }
}
