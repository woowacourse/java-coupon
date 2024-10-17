package coupon.service.coupon;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponRepository;
import coupon.exception.CouponException;
import org.springframework.stereotype.Service;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new CouponException("id(%s)에 해당하는 쿠폰이 존재하지 않습니다.".formatted(id)));
    }

    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }

}
