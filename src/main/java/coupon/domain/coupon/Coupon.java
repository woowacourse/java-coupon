package coupon.domain.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private CouponName name;

    private DiscountAmount discountAmount;

    private DiscountPercent discountPercent;

    private MinimumAmount minimumAmount;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    private IssuancePeriod issuancePeriod;

    public Coupon(
            String name,
            int discountAmount,
            int minimumAmount,
            Category category,
            LocalDate startDate,
            LocalDate endDate
    ) {
        this.name = new CouponName(name);
        this.discountAmount = new DiscountAmount(discountAmount);
        this.minimumAmount = new MinimumAmount(minimumAmount);
        this.discountPercent = calcDiscountPercent(discountAmount, minimumAmount);
        this.category = category;
        this.issuancePeriod = new IssuancePeriod(startDate, endDate);
    }

    private DiscountPercent calcDiscountPercent(int discountAmount, int minimumAmount) {
        float result = (float) discountAmount / minimumAmount * 100;
        return new DiscountPercent((int) Math.floor(result));
    }
}
