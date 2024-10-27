package coupon.service;

import coupon.domain.Coupon;
import coupon.dto.request.CouponSaveRequest;
import coupon.repository.CouponRepository;
import coupon.util.FallbackExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final FallbackExecutor fallbackExecutor;

    @Transactional
    public Coupon save(CouponSaveRequest couponSaveRequest) {
        Coupon coupon = couponSaveRequest.toCoupon();
        return couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon findById(long couponId) {
        return couponRepository.findById(couponId)
                .orElse(retryFindById(couponId));
    }

    private Coupon retryFindById(long couponId) {
        return fallbackExecutor.execute(() -> couponRepository.findById(couponId))
                .orElseThrow(() -> new IllegalArgumentException("쿠폰이 존재하지 않습니다."));
    }
}
