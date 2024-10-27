package coupon.service;

import coupon.domain.Coupon;
import coupon.repository.CouponInMemoryRepository;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponInMemoryRepository couponInMemoryRepository;

    @Transactional
    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long id) {
        return couponInMemoryRepository.getCoupon(id);
    }
}
