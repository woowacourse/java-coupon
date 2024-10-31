package coupon.coupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponReader {

    private final CouponRepository couponRepository;

    @Cacheable(cacheNames = "coupons", key = "#couponId")
    @Transactional(readOnly = true)
    public Coupon getCoupon(long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("해당 쿠폰 아이디와 일치하는 쿠폰을 찾을 수 없습니다. 입력된 id:" + couponId));
    }
}
