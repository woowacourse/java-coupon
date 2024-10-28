package coupon.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import coupon.domain.coupon.Coupon;

@Repository
public class CouponCache {

    private static final Map<Long, Coupon> COUPON_CACHE = new HashMap<>();

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
