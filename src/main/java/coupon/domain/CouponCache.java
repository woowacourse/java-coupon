package coupon.domain;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class CouponCache {

    private final Map<Long, Coupon> coupons = new ConcurrentHashMap<>();

    public void save(Coupon coupon) {
        coupons.put(coupon.getId(), coupon);
    }

    public Optional<Coupon> findById(Long couponId) {
        return Optional.ofNullable(coupons.get(couponId));
    }
}
