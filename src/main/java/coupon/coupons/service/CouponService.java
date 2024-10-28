package coupon.coupons.service;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import coupon.coupons.domain.Coupon;
import coupon.coupons.repository.CouponRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponService {

    private final CouponRepository couponRepository;

    @CachePut(value = "coupons", key = "#result.id")
    @Transactional
    public Coupon create(Coupon coupon) {
        Coupon savedCoupon = couponRepository.save(coupon);
        return savedCoupon;
    }

    @Cacheable(value = "coupons", key = "#id")
    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));
    }
}
