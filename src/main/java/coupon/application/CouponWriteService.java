package coupon.application;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponWriteService {

    private final CouponRepository couponRepository;

    @CachePut(cacheNames = "coupon", key = "#coupon.id")
    @CacheEvict(cacheNames = "coupon", key = "'all'")
    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }
}
