package coupon.coupon.domain;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Coupons {

    private final List<Coupon> coupons;

    public Coupons(Coupon... coupons) {
        this.coupons = List.of(coupons);
    }

    public Coupon getCouponByOrder(int order) {
        return coupons.get(order);
    }
}
