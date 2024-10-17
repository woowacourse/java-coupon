package coupon.domain.coupon;

import java.time.LocalDate;

public class Coupon {

    private final Name name;
    private final MinimumOrderAmount minimumOrderAmount;
    private final DiscountAmount discountAmount;
    private final Category category;
    private final PeriodOfIssuance periodOfIssuance;

    public Coupon(String name, int minimumOrderAmount, int discountAmount, String category, LocalDate startDate,
                  LocalDate endDate) {
        this.name = new Name(name);
        this.minimumOrderAmount = new MinimumOrderAmount(minimumOrderAmount);
        this.discountAmount = new DiscountAmount(discountAmount, minimumOrderAmount);
        this.category = Category.valueOf(category);
        this.periodOfIssuance = new PeriodOfIssuance(startDate, endDate);
    }
}
