package coupon.coupon.domain;

import java.time.LocalDate;

public class Coupon {

    private final String name;
    private final Discount discount;
    private final int minimumOrderAmount;
    private final Category category;
    private final LocalDate startAt;
    private final LocalDate endAt;

    public Coupon(String name, int price, int minimumOrderAmount, Category category, LocalDate startAt, LocalDate endAt) {
        this.name = name;
        this.discount = new Discount(price, minimumOrderAmount);
        this.minimumOrderAmount = minimumOrderAmount;
        this.category = category;
        this.startAt = startAt;
        this.endAt = endAt;
    }
}
