package coupon.domain.coupon;

public class Coupon {

    private final Long id;

    private final Name name;

    private final DiscountPolicy discountPolicy;

    private final CouponCategory couponCategory;

    private final CouponPeriod couponPeriod;


    public Coupon(
            Long id,
            Name name,
            DiscountPolicy discountPolicy,
            CouponCategory couponCategory,
            CouponPeriod couponPeriod
    ) {
        this.id = id;
        this.name = name;
        this.discountPolicy = discountPolicy;
        this.couponCategory = couponCategory;
        this.couponPeriod = couponPeriod;
    }
}
