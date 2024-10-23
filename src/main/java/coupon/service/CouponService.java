package coupon.service;

import coupon.domain.Coupon;
import coupon.repository.CouponRepository;
import java.util.Optional;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponService {

    private static final String CACHE_KEY_COUPON = "coupons";

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @CachePut(value = CACHE_KEY_COUPON, key = "#result.id")
    @Transactional
    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Cacheable(value = CACHE_KEY_COUPON, key = "#id")
    @Transactional(readOnly = true)
    public Optional<Coupon> getCoupon(@Param("id") long id) {
        return couponRepository.findById(id);
    }
}
