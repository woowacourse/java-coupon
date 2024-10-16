package coupon.coupon.service;

import coupon.coupon.dto.CouponCreateRequest;
import coupon.coupon.domain.Coupon;
import coupon.coupon.dto.CouponResponse;
import coupon.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public CouponResponse createCoupon(CouponCreateRequest request) {
        Coupon coupon = request.toCoupon();
        Coupon savedCoupon = couponRepository.save(coupon);
        return CouponResponse.from(savedCoupon);
    }

    @Transactional(readOnly = true)
    public CouponResponse readCoupon(Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));
        return CouponResponse.from(coupon);
    }
}
