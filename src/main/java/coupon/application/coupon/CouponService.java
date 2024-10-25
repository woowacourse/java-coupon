package coupon.application.coupon;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponRepository;
import coupon.exception.CouponException;
import coupon.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class CouponService {

    private static final String COUPON_CACHE_NAME = "coupon";

    private final CouponRepository couponRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = COUPON_CACHE_NAME, key = "#id")
    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new CouponException(ExceptionType.COUPON_NOT_FOUND));
    }

    @CachePut(value = COUPON_CACHE_NAME, key = "#coupon.id")
    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }
}
