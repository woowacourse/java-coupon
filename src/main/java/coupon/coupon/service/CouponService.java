package coupon.coupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.DiscountPolicy;
import coupon.coupon.dto.CouponCreateRequest;
import coupon.coupon.dto.CouponResponse;
import coupon.coupon.entity.CouponEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponWriterService couponWriterService;
    private final CouponReaderService couponReaderService;
    private final List<DiscountPolicy> discountPolicies;

    public CouponEntity create(CouponCreateRequest couponCreateRequest) {
        Coupon coupon = new Coupon(couponCreateRequest.name(), discountPolicies, couponCreateRequest.discountAmount(),
                couponCreateRequest.minimumOrderAmount(), couponCreateRequest.category(),
                couponCreateRequest.startDate(), couponCreateRequest.endDate());
        return couponWriterService.create(coupon);
    }

    @Cacheable(value = "coupons", key = "#couponId")
    public CouponResponse getCoupon(long couponId) {
        Optional<CouponEntity> coupon = couponReaderService.getCoupon(couponId);
        CouponEntity couponEntity = coupon.orElseGet(() -> couponWriterService.getCoupon(couponId));
        return CouponResponse.from(couponEntity);
    }
}
