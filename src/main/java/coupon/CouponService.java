package coupon;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;

    private final Map<Long, Coupon> cache = new HashMap<>();

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long id) {
        if (cache.containsKey(id)) {
            return cache.get(id);
        }
        return couponRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
        cache.put(coupon.getId(), coupon);
    }
}
