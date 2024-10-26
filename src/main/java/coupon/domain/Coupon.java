package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    private static final int MIN_DISCOUNT_RATE = 3;
    private static final int MAX_DISCOUNT_RATE = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CouponName name;

    @Embedded
    private DiscountAmount discountAmount;

    @Embedded
    private MinOrderAmount minOderAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Embedded
    @Column(nullable = false)
    private IssuablePeriod issuablePeriod;

    public Coupon(Long id, CouponName name, DiscountAmount discountAmount, MinOrderAmount minOderAmount,
                  Category category, IssuablePeriod issuablePeriod) {
        validate(discountAmount, minOderAmount);
        this.id = id;
        this.name = name;
        this.discountAmount = discountAmount;
        this.minOderAmount = minOderAmount;
        this.category = category;
        this.issuablePeriod = issuablePeriod;
    }

    public Coupon(CouponName name, DiscountAmount discountAmount, MinOrderAmount minOderAmount, Category category,
                  IssuablePeriod issuablePeriod) {
        this(null, name, discountAmount, minOderAmount, category, issuablePeriod);
    }

    private void validate(DiscountAmount discountAmount, MinOrderAmount minOderAmount) {
        int discountRate = (int) ((double) discountAmount.getAmount() / minOderAmount.getAmount() * 100);
        if (discountRate < MIN_DISCOUNT_RATE || discountRate > MAX_DISCOUNT_RATE) {
            throw new IllegalArgumentException(
                    "할인율은 " + MIN_DISCOUNT_RATE + "% 이상 " + MAX_DISCOUNT_RATE + "% 이하여야 합니다.");
        }
    }
}
