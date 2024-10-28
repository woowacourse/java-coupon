package coupon.support;

import coupon.domain.coupon.Coupon;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class CouponCache {

    private final Map<Long, Coupon> cache = new ConcurrentHashMap<>();

    public Coupon getCoupon(Long id) {
        return cache.get(id);
    }

    public void putCoupon(Long id, Coupon coupon) {
        cache.put(id, coupon);
    }

    public void removeCoupon(Long id) {
        cache.remove(id);
    }
}
