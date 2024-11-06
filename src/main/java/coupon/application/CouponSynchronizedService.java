package coupon.application;

import coupon.domain.Coupon;
import coupon.domain.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponSynchronizedService {

    private final CouponRepository couponRepository;

    @Transactional
    public void updateDiscountAmount(Long couponId, int discountAmount) {

    }

    @Transactional
    public void updateMinOrderAmount(Long couponId, int minOrderAmount) {

    }

    private Coupon getCoupon(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰이 존재하지 않습니다."));
    }
}
