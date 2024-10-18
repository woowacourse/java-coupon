package coupon.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponName;
import coupon.service.port.CouponRepository;

public class FakeCouponRepository implements CouponRepository {

    private final List<Coupon> storage = new ArrayList<>();

    @Override
    public Optional<Coupon> findByName(final CouponName name) {
        return storage.stream()
                .filter(coupon -> coupon.getNameValue().equals(name.getValue())).findAny();
    }

    @Override
    public void save(final Coupon coupon) {
        storage.add(coupon);
        System.out.println();
    }

    public Coupon getLast() {
        return storage.get(storage.size() - 1);
    }

    public void addItem(final Coupon coupon) {
        storage.add(coupon);
    }
}
