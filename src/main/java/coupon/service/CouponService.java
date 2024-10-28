package coupon.service;

import coupon.domain.Coupon;
import coupon.dto.CouponRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponCommandService couponCommandService;
    private final CouponQueryService couponQueryService;

    public Long create(CouponRequest request) {
        return couponCommandService.create(request);
    }

    @Cacheable(key = "#id", value = "coupon")
    public Coupon getCoupon(Long id) {
        try {
            return couponQueryService.getCoupon(id);
        } catch (IllegalArgumentException e) {
            return couponCommandService.getCoupon(id);
        }
    }
}
