package coupon.service;

import coupon.domain.Coupon;
import coupon.repository.CouponRepository;
import coupon.service.exception.CouponBusinessLogicException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new CouponBusinessLogicException("Coupon not found ID = " + id));
    }

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }
}
