package coupon.fixture;

import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import java.time.LocalDate;

public enum CouponFixture {

    PRAM_COUPON("pram", 1_000, 30_000, Category.FOOD, LocalDate.now(), LocalDate.now());

    private final String name;
    private final int discountAmount;
    private final int minimumAmount;
    private final Category category;
    private final LocalDate startDate;
    private final LocalDate endDate;

    CouponFixture(
            String name,
            int discountAmount,
            int minimumAmount,
            Category category,
            LocalDate startDate,
            LocalDate endDate
    ) {
        this.name = name;
        this.discountAmount = discountAmount;
        this.minimumAmount = minimumAmount;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Coupon coupon() {
        return new Coupon(name, discountAmount, minimumAmount, category, startDate, endDate);
    }
}
