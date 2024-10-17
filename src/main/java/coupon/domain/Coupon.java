package coupon.domain;

import coupon.exception.ErrorMessage;
import coupon.exception.GlobalCustomException;
import jakarta.persistence.Column;
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

    private static final int NAME_MAX_LENGTH = 30;
    private static final int DISCOUNT_AMOUNT_UNIT = 500;
    private static final int DISCOUNT_AMOUNT_MINIMUM = 1_000;
    private static final int DISCOUNT_AMOUNT_MAXIMUM = 10_000;
    private static final int MINIMUM_ORDER_PRICE_MINIMUM = 5_000;
    private static final int MINIMUM_ORDER_PRICE_MAXIMUM = 100_000;
    private static final int DISCOUNT_RATE_MINIMUM = 3;
    private static final int DISCOUNT_RATE_MAXIMUM = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "discount_amount", nullable = false)
    private int discountAmount;

    @Column(name = "minimum_order_price", nullable = false)
    private int minimumOrderPrice;

    @Column(name = "category", columnDefinition = "varchar(20)")
    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Column(name = "issue_started_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime issueStartedAt;

    @Column(name = "issue_ended_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime issueEndedAt;

    public Coupon(String name, int discountAmount, int minimumOrderPrice, Category category,
                  LocalDateTime issueStartedAt, LocalDateTime issueEndedAt) {
        validateName(name);
        validateDiscountAmount(discountAmount);
        validateMinimumOrderPrice(minimumOrderPrice);
        validateDiscountRate(discountAmount, minimumOrderPrice);
        validateIssuePeriod(issueStartedAt, issueEndedAt);
        this.name = name;
        this.discountAmount = discountAmount;
        this.minimumOrderPrice = minimumOrderPrice;
        this.category = category;
        this.issueStartedAt = issueStartedAt;
        this.issueEndedAt = issueEndedAt;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank() || name.length() > NAME_MAX_LENGTH) {
            throw new GlobalCustomException(ErrorMessage.INVALID_COUPON_NAME);
        }
    }

    private void validateDiscountAmount(int discountAmount) {
        if (discountAmount % DISCOUNT_AMOUNT_UNIT != 0) {
            throw new GlobalCustomException(ErrorMessage.INVALID_COUPON_DISCOUNT_AMOUNT_UNIT);
        }

        if (discountAmount < DISCOUNT_AMOUNT_MINIMUM || discountAmount > DISCOUNT_AMOUNT_MAXIMUM) {
            throw new GlobalCustomException(ErrorMessage.INVALID_COUPON_DISCOUNT_AMOUNT_RANGE);
        }
    }

    private void validateMinimumOrderPrice(int minimumOrderPrice) {
        if (minimumOrderPrice < MINIMUM_ORDER_PRICE_MINIMUM || minimumOrderPrice > MINIMUM_ORDER_PRICE_MAXIMUM) {
            throw new GlobalCustomException(ErrorMessage.INVALID_MINIMUM_ORDER_PRICE_RANGE);
        }
    }

    private void validateDiscountRate(int discountAmount, int minimumOrderPrice) {
        int discountRate = discountAmount * 100 / minimumOrderPrice;
        if (discountRate < DISCOUNT_RATE_MINIMUM || discountRate > DISCOUNT_RATE_MAXIMUM) {
            throw new GlobalCustomException(ErrorMessage.INVALID_DISCOUNT_RATE_RANGE);
        }
    }

    private void validateIssuePeriod(LocalDateTime issueStartedAt, LocalDateTime issueEndedAt) {
        if (issueStartedAt.isAfter(issueEndedAt)) {
            throw new GlobalCustomException(ErrorMessage.INVALID_ISSUE_PERIOD);
        }
    }
}
