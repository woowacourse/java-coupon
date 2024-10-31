package coupon.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import coupon.domain.coupon.Coupon;

public class CouponCache {

    private static final Map<Long, Coupon> COUPON_CACHE = new ConcurrentHashMap<>();

    private CouponCache() {
    }

    public static Coupon getCoupon(Long id) {
        return COUPON_CACHE.get(id);
    }

    public static void putCoupon(Coupon coupon) {
        COUPON_CACHE.put(coupon.getId(), coupon);
    }

    public static void clear() {
        COUPON_CACHE.clear();
    }
}
