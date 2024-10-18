package coupon.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Embedded
    private CouponName name;

    @NotNull
    @Embedded
    private DiscountAmount discountAmount;

    @NotNull
    @Embedded
    private MinOrderAmount minOderAmount;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Category category;

    @NotNull
    @Embedded
    private IssuancePeriod issuanceDate;

    public Coupon(Long id, CouponName name, DiscountAmount discountAmount, MinOrderAmount minOderAmount,
                  Category category, IssuancePeriod issuanceDate) {
        validate(discountAmount, minOderAmount);
        this.id = id;
        this.name = name;
        this.discountAmount = discountAmount;
        this.minOderAmount = minOderAmount;
        this.category = category;
        this.issuanceDate = issuanceDate;
    }

    public Coupon(CouponName name, DiscountAmount discountAmount, MinOrderAmount minOderAmount, Category category,
                  IssuancePeriod issuanceDate) {
        this(null, name, discountAmount, minOderAmount, category, issuanceDate);
    }

    private void validate(DiscountAmount discountAmount, MinOrderAmount minOderAmount) {
        int discountRate = (int) (discountAmount.getDiscountAmount() / minOderAmount.getMinOrderAmount() * 100);

        if (discountRate < 3 || discountRate > 20) {
            throw new IllegalArgumentException("할인율은 3% 이상 20% 이하여야 합니다.");
        }
    }
}
