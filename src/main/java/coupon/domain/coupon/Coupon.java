package coupon.domain.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    private static final int MIN_RATE = 3;
    private static final int MAX_RATE = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Name name;

    @Embedded
    private DiscountAmount discountAmount;

    @Embedded
    private MinOrderAmount minOrderAmount;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Embedded
    private IssuancePeriod issuancePeriod;

    public Coupon(
            Long id,
            Name name,
            DiscountAmount discountAmount,
            MinOrderAmount minOrderAmount,
            Category category,
            IssuancePeriod issuancePeriod
    ) {
        validateDiscountRate(discountAmount, minOrderAmount);
        this.id = id;
        this.name = name;
        this.discountAmount = discountAmount;
        this.minOrderAmount = minOrderAmount;
        this.category = category;
        this.issuancePeriod = issuancePeriod;
    }

    private void validateDiscountRate(DiscountAmount discountAmount, MinOrderAmount minOrderAmount) {
        int rate = calculateDiscountRate(discountAmount, minOrderAmount);
        if (rate < MIN_RATE || rate > MAX_RATE) {
            throw new IllegalArgumentException("할인율은 " + MIN_RATE + "% 이상, " + MAX_RATE + "% 이하여야 합니다.");
        }
    }

    private int calculateDiscountRate(DiscountAmount discountAmount, MinOrderAmount minOrderAmount) {
        return (int) ((double) discountAmount.getValue() / minOrderAmount.getValue() * 100);
    }
}
