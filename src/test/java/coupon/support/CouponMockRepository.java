package coupon.support;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

import coupon.domain.Coupon;
import coupon.domain.CouponRepository;

public class CouponMockRepository implements CouponRepository {

    private final AtomicLong id = new AtomicLong(1L);
    private final HashMap<AtomicLong, Coupon> coupons = new HashMap<>();

    @Override
    public Coupon save(Coupon coupon) {
        Coupon newCoupon = new Coupon(id.getAndAdd(1L), coupon.getName(), coupon.getDiscountAmount(), coupon.getMinimumOrderAmount(), coupon.getStartDate(), coupon.getEndDate(), coupon.getCategory());
        coupons.put(id, newCoupon);
        return newCoupon;
    }

    @Override
    public Coupon findById(long id) {
        if (!coupons.containsKey(id)) {
            throw new IllegalArgumentException("id에 해당하는 쿠폰이 존재하지 않습니다.");
        }
        return coupons.get(id);
    }
}
