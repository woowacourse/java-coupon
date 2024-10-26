package coupon.coupon.service;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.coupon.domain.Coupon;
import coupon.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {
    private static final String CACHE_NAMES = "coupons";

    private final CouponRepository couponRepository;

    @Transactional
    @CachePut(value = CACHE_NAMES, key = "#result.id")
    public Coupon createWithCache(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = CACHE_NAMES, key = "#couponId")
    public Coupon readByIdFromReaderWithCache(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 ID의 쿠폰이 없습니다."));
    }
}
