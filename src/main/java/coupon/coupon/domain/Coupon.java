package coupon.coupon.domain;

import java.time.LocalDate;

public class Coupon {

    private  static final int MAX_NAME_LENGTH = 30;

    private final String name;
    private final Discount discount;
    private final int minimumOrderAmount;
    private final Category category;
    private final LocalDate startAt;
    private final LocalDate endAt;

    public Coupon(String name, int price, int minimumOrderAmount, Category category, LocalDate startAt, LocalDate endAt) {
        validateName(name);
        this.name = name;
        this.discount = new Discount(price, minimumOrderAmount);
        this.minimumOrderAmount = minimumOrderAmount;
        this.category = category;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    private void validateName(String name) {
        if(name.length() <= MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Name is too long");
        }
    }
}
