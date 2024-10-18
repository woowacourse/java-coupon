package coupon.domain.coupon;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class Coupon {

    private final Long id;
    private final Name name;
    private final DiscountAmount discountAmount;
    private final MinOrderAmount minOrderAmount;
    private final DiscountRange discountRange;
    private final Category category;
    private final IssueDuration issueDuration;

    public Coupon(Long id, String name, long discountAmount, long minOrderAmount,
                  Category category, LocalDate issueStartDate, LocalDate issueEndDate) {
        this.id = id;
        this.name = new Name(name);
        this.discountAmount = new DiscountAmount(discountAmount);
        this.minOrderAmount = new MinOrderAmount(minOrderAmount);
        this.discountRange = new DiscountRange(discountAmount, minOrderAmount);
        this.category = category;
        this.issueDuration = new IssueDuration(issueStartDate, issueEndDate);
    }
}
