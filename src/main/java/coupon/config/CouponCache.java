package coupon.config;

import coupon.data.Coupon;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class CouponCache {
    private static final ConcurrentHashMap<Long, Coupon> value = new ConcurrentHashMap<>();

    public static void cache(Coupon coupon) {
        value.put(coupon.getId(), coupon);
    }

    public static void delete(long id) {
        value.remove(id);
    }

    public static boolean exists(long id) {
        return value.contains(id);
    }

    public static Coupon get(long id) {
        return value.get(id);
    }

}
