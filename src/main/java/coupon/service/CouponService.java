package coupon.service;

import coupon.domain.Coupon;
import coupon.dto.request.CouponSaveRequest;
import coupon.dto.response.CouponResponse;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public Coupon save(CouponSaveRequest couponSaveRequest) {
        Coupon coupon = couponSaveRequest.toCoupon();
        return couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public CouponResponse findById(long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰이 존재하지 않습니다."));
        return CouponResponse.from(coupon);
    }
}
