package coupon.domain;

import java.util.Objects;

public class Coupon {

    private final Name name;
    private final DiscountMoney discountMoney;
    private final DiscountRate discountRate;
    private final OrderPrice orderPrice;
    private final Category category;
    private final IssuedPeriod issuedPeriod;

    public Coupon(Name name, DiscountMoney discountMoney, DiscountRate discountRate, OrderPrice orderPrice,
                  Category category, IssuedPeriod issuedPeriod) {
        this.name = Objects.requireNonNull(name);
        this.discountMoney = Objects.requireNonNull(discountMoney);
        this.discountRate = Objects.requireNonNull(discountRate);
        this.orderPrice = Objects.requireNonNull(orderPrice);
        this.category = Objects.requireNonNull(category);
        this.issuedPeriod = Objects.requireNonNull(issuedPeriod);
    }
}
