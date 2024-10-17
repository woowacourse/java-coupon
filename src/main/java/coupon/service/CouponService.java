package coupon.service;

import coupon.domain.Coupon;
import coupon.repository.CouponRepository;
import coupon.service.exception.CouponBusinessLogicException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new CouponBusinessLogicException("Coupon not found ID = " + id));
    }

    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }
}
