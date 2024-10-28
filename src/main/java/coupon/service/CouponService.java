package coupon.service;

import coupon.domain.Coupon;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @CachePut(value = "CouponCache", key = "#coupon.id")
    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Cacheable(value = "CouponCache", key = "#id")
    public Coupon getCoupon(long id) {
        return couponRepository.findById(id)
                .orElseThrow();
    }
}
