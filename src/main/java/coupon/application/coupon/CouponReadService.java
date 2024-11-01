package coupon.application.coupon;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponRepository;
import coupon.exception.CouponException;
import coupon.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CouponReadService {

    private static final String COUPON_CACHE_NAME = "coupon";

    private final CouponRepository couponRepository;

    @Cacheable(value = COUPON_CACHE_NAME, key = "#id")
    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new CouponException(ExceptionType.COUPON_NOT_FOUND));
    }
}
