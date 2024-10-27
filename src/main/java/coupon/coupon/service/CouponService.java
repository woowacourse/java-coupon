package coupon.coupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.dto.CouponResponse;
import coupon.coupon.entity.CouponEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponWriterService couponWriterService;
    private final CouponReaderService couponReaderService;

    public CouponEntity create(Coupon coupon) {
        return couponWriterService.create(new CouponEntity(coupon));
    }

    public CouponResponse getCoupon(long couponId) {
        Optional<CouponEntity> coupon = couponReaderService.getCoupon(couponId);
        CouponEntity couponEntity = coupon.orElseGet(() -> couponWriterService.getCoupon(couponId));
        return CouponResponse.from(couponEntity);
    }
}
