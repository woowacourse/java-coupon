package coupon.service;

import coupon.domain.Coupon;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponWriterService {

    private final CouponRepository couponRepository;

    public Coupon getCoupon(long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow();
    }

    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }
}
