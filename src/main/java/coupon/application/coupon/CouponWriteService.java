package coupon.application.coupon;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class CouponWriteService {

    private static final String COUPON_CACHE_NAME = "coupon";

    private final CouponRepository couponRepository;

    @CachePut(value = COUPON_CACHE_NAME, key = "#coupon.id")
    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }
}
