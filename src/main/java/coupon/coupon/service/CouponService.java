package coupon.coupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.dto.CouponRequest;
import coupon.coupon.dto.CouponResponse;
import coupon.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponReader couponReader;
    private final CouponValidator couponValidator;
    private final CouponRepository couponRepository;

    @Transactional
    public CouponResponse create(CouponRequest request) {
        couponValidator.validateAmount(request.discountAmount(), request.minOrderAmount());

        Coupon coupon = couponRepository.save(request.toEntity());
        return CouponResponse.from(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(long couponId) {
        return couponRepository.findById(couponId)
                .orElseGet(() -> couponReader.getCouponFromWriteDb(couponId));
    }
}
