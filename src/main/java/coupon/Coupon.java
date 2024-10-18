package coupon;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Coupon {

    private final Name name;
    private final DiscountAmount discountAmount;
    private final PurchaseAmount purchaseAmount;
    private final Category category;
    private final IssuancePeriod issuancePeriod;

    public Coupon(
            String name,
            Integer discountAmount,
            Integer purchaseAmount,
            Category category,
            LocalDate startDate,
            LocalDate endDate
    ) {
        validateDiscountRate(discountAmount, purchaseAmount);
        this.name = new Name(name);
        this.discountAmount = new DiscountAmount(discountAmount);
        this.purchaseAmount = new PurchaseAmount(purchaseAmount);
        this.category = category;
        this.issuancePeriod = new IssuancePeriod(startDate, endDate);
    }

    private static void validateDiscountRate(Integer discountAmount, Integer purchaseAmount) {
        int purchaseRate = discountAmount * 100 / purchaseAmount;
        if (purchaseRate < 3 || purchaseRate > 20) {
            throw new IllegalArgumentException("할인율은 3% 이상, 20% 이하여야 합니다.");
        }
    }
}
