package coupon.service;

import coupon.domain.Coupon;
import coupon.repository.CouponRepository;
import coupon.util.FallbackExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final FallbackExecutor fallbackExecutor;

    @Transactional
    @CachePut(value = "coupon", key = "#result.id", condition = "#result != null")
    public Coupon save(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "coupon", key = "#couponId")
    public Coupon findById(long couponId) {
        return couponRepository.findById(couponId)
                .orElse(retryFindById(couponId));
    }

    private Coupon retryFindById(long couponId) {
        return fallbackExecutor.execute(() -> couponRepository.findById(couponId))
                .orElseThrow(() -> new IllegalArgumentException("쿠폰이 존재하지 않습니다."));
    }
}
