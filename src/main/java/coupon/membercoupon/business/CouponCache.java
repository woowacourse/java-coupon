package coupon.membercoupon.business;

import coupon.coupon.domain.Coupon;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class CouponCache {

    private static final Map<Long, Coupon> CACHED_COUPONS = new ConcurrentHashMap<>();

    public static void add(Long couponId, Coupon coupon) {
        CACHED_COUPONS.put(couponId, coupon);
    }

    public static Coupon get(Long couponId) {
        return CACHED_COUPONS.get(couponId);
    }
}
