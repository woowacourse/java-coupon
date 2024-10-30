package coupon.config;

import coupon.data.CouponEntity;
import java.util.concurrent.ConcurrentHashMap;

public class CouponCache {

    private CouponCache(){

    }

    private static final ConcurrentHashMap<Long, CouponEntity> value = new ConcurrentHashMap<>();

    public static void cache(CouponEntity couponEntity) {

        value.put(couponEntity.getId(), couponEntity);
    }

    public static void delete(long id) {
        value.remove(id);
    }

    public static boolean exists(long id) {
        return value.contains(id);
    }

    public static CouponEntity get(long id) {
        return value.get(id);
    }
}
