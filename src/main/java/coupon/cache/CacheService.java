package coupon.cache;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import coupon.CouponException;
import coupon.coupon.domain.Coupon;
import coupon.coupon.repository.CouponRepository;
import coupon.membercoupon.domain.MemberCoupon;

@Service
public class CacheService {

    private static final String NO_COUPON_MESSAGE = "요청하신 쿠폰을 찾을 수 없어요.";
    private final CouponRepository couponRepository;

    public CacheService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Cacheable(value = "coupons", key = "#p0.couponId")
    public Coupon getCoupon(MemberCoupon memberCoupon) {
        return getCouponById(memberCoupon.getCouponId());
    }

    @Cacheable(value = "coupons", key = "#p0")
    public Coupon getCoupon(long id) {
        return getCouponById(id);
    }

    private Coupon getCouponById(Long id) {
        return couponRepository.findById(id).orElseThrow(() -> new CouponException(NO_COUPON_MESSAGE));
    }
}
