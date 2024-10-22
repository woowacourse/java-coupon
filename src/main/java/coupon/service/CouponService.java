package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.repository.CouponRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional(readOnly = true)
@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponCache couponCache;

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
        couponCache.cache(coupon);
    }

    public Coupon getCoupon(Long id) {
        Coupon cachedCoupon = couponCache.get(id);
        if (cachedCoupon != null) {
            return cachedCoupon;
        }

        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰이 존재하지 않습니다."));
        couponCache.cache(coupon);
        return coupon;
    }
}
