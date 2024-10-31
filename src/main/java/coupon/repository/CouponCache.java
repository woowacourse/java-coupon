package coupon.repository;

import coupon.domain.Coupon;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CouponCache {

    private final ConcurrentHashMap<Long, Coupon> coupons = new ConcurrentHashMap<>();

    public Optional<Coupon> getCoupon(Long id) {
        return Optional.ofNullable(coupons.get(id));
    }

    public void putCoupon(Coupon coupon) {
        coupons.put(coupon.getId(), coupon);
    }
}
