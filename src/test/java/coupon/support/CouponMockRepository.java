package coupon.support;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

import coupon.domain.Coupon;
import coupon.domain.CouponRepository;

public class CouponMockRepository implements CouponRepository {

    private final AtomicLong id = new AtomicLong(1L);
    private final HashMap<Long, Coupon> coupons = new HashMap<>();

    @Override
    public Coupon save(Coupon coupon) {
        if (coupon.getId() == null) {
            long newId = id.getAndIncrement();
            Coupon newCoupon = new Coupon(newId, coupon.getName(), coupon.getDiscountAmount(), coupon.getMinimumOrderAmount(), coupon.getStartDate(), coupon.getEndDate(), coupon.getCategory());
            coupons.put(newId, newCoupon);
            return newCoupon;
        }
        coupons.put(coupon.getId(), coupon);
        return coupon;
    }

    @Override
    public Coupon findById(long id) {
        if (!coupons.containsKey(id)) {
            throw new IllegalArgumentException("id에 해당하는 쿠폰이 존재하지 않습니다.");
        }
        return coupons.get(id);
    }
}
