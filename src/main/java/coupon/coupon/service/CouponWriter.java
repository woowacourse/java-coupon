package coupon.coupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.repository.CouponRepository;
import coupon.global.cache.CacheConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponWriter {

    private final CouponRepository couponRepository;

    @CachePut(cacheNames = CacheConstants.COUPON_CACHE_NAME, key = "#result.id")
    @Transactional
    public Coupon save(Coupon coupon) {
        return couponRepository.save(coupon);
    }
}
