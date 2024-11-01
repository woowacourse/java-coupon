package coupon.coupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.dto.CouponRequest;
import coupon.coupon.dto.CouponResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponReader couponReader;
    private final CouponWriter couponWriter;
    private final CouponValidator couponValidator;

    @Transactional
    public CouponResponse create(CouponRequest request) {
        couponValidator.validateAmount(request.discountAmount(), request.minOrderAmount());

        Coupon coupon = couponWriter.save(request.toEntity());
        return CouponResponse.from(coupon);
    }

    @Transactional(readOnly = true)
    public CouponResponse getCoupon(long couponId) {
        Coupon coupon = couponReader.getCoupon(couponId);
        return CouponResponse.from(coupon);
    }
}
