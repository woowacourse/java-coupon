package coupon.domain;

import coupon.dto.CouponResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class CouponCache {

    private final Map<Long, CouponResponse> coupons = new ConcurrentHashMap<>();

    public void save(Coupon coupon) {
        coupons.put(coupon.getId(), CouponResponse.from(coupon));
    }

    public CouponResponse findById(Long couponId) {
        return coupons.get(couponId);
    }
}
