package coupon.coupon.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CouponName name;

    @Embedded
    private DiscountAmount discountAmount;

    @Embedded
    private MinOrderAmount minOrderAmount;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Embedded
    private Period period;

    public Coupon(String name, int discountAmount, int minOrderAmount, String category, LocalDate startDate, LocalDate endDate) {
        this.name = new CouponName(name);
        this.discountAmount = new DiscountAmount(discountAmount);
        this.minOrderAmount = new MinOrderAmount(minOrderAmount);
        this.category = Category.from(category);
        this.period = new Period(startDate, endDate);
    }

    public boolean isNotBetweenIssuancePeriod(LocalDate issuanceDate) {
        return period.isNotBetweenIssuancePeriod(issuanceDate);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public int getDiscountAmount() {
        return discountAmount.getValue();
    }

    public int getMinOrderAmount() {
        return minOrderAmount.getValue();
    }

    public String getCategory() {
        return category.getValue();
    }

    public Period getPeriod() {
        return period;
    }
}
