package coupon.domain;

import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import java.time.LocalDate;

public enum CouponFixture {

    FOOD_COUPON("food coupon", 10000, 1000, Category.FOOD, LocalDate.now(), LocalDate.now().plusDays(7)),
    FASHION_COUPON("fashion coupon", 20000, 2000, Category.FASHION, LocalDate.now(), LocalDate.now().plusDays(7));

    private final String name;
    private final int minimumOrderAmount;
    private final int discountAmount;
    private final Category category;
    private final LocalDate beginDate;
    private final LocalDate endDate;

    CouponFixture(String name, int minimumOrderAmount, int discountAmount, Category category, LocalDate beginDate,
                  LocalDate endDate) {
        this.name = name;
        this.discountAmount = discountAmount;
        this.minimumOrderAmount = minimumOrderAmount;
        this.category = category;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public Coupon create() {
        return new Coupon(name, minimumOrderAmount, discountAmount, category, beginDate, endDate);
    }
}
