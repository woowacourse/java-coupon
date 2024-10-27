package coupon.domain;

import coupon.domain.vo.DiscountAmount;
import coupon.domain.vo.IssuePeriod;
import coupon.domain.vo.MinimumOrderPrice;
import coupon.domain.vo.Name;
import coupon.exception.ErrorMessage;
import coupon.exception.GlobalCustomException;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseTime {

    private static final int DISCOUNT_RATE_MINIMUM = 3;
    private static final int DISCOUNT_RATE_MAXIMUM = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Embedded
    private Name name;

    @Embedded
    private DiscountAmount discountAmount;

    @Embedded
    private MinimumOrderPrice minimumOrderPrice;

    @Column(name = "category", nullable = false, columnDefinition = "varchar(20)")
    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Embedded
    private IssuePeriod issuePeriod;

    public Coupon(Name name, DiscountAmount discountAmount, MinimumOrderPrice minimumOrderPrice, Category category,
                  IssuePeriod issuePeriod) {
        validateDiscountRate(discountAmount, minimumOrderPrice);
        this.name = name;
        this.discountAmount = discountAmount;
        this.minimumOrderPrice = minimumOrderPrice;
        this.category = category;
        this.issuePeriod = issuePeriod;
    }

    private void validateDiscountRate(DiscountAmount discountAmount, MinimumOrderPrice minimumOrderPrice) {
        int discountRate = discountAmount.calculateDiscountRate(minimumOrderPrice.getValue());
        if (discountRate < DISCOUNT_RATE_MINIMUM || discountRate > DISCOUNT_RATE_MAXIMUM) {
            throw new GlobalCustomException(ErrorMessage.INVALID_DISCOUNT_RATE_RANGE);
        }
    }

    public boolean canIssue(LocalDateTime issueDateTime) {
        return this.issuePeriod.isInIssuePeriod(issueDateTime);
    }
}
