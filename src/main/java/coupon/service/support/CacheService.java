package coupon.service.support;

import coupon.domain.Coupon;
import coupon.entity.MemberCouponEntity;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CacheService {

    private final CouponRepository couponRepository;

    @Cacheable(value = "coupons", key = "#memberCoupon.couponId")
    public Coupon getCoupon(MemberCouponEntity memberCoupon) {
        return getCouponById(memberCoupon.getCouponId());
    }

    @Cacheable(value = "coupons", key = "#id")
    public Coupon getCoupon(Long id) {
        return getCouponById(id);
    }

    private Coupon getCouponById(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."))
                .toDomain();
    }
}
