package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coupon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    /*
    쿠폰 이름은 30자 이하여야 한다.
     */
    private static final int NAME_MAX_LENGTH = 30;

    /*
    쿠폰 만료일은 기본 1달로 한다.
     */
    private static final int EXPIRATION_MONTH = 1;

    /*
    할인 금액은 1,000원 이상, 10,000원 이하여야 한다. 500원 단위로 설정할 수 있다.
     */
    private static final int DISCOUNT_AMOUNT_MIN = 1000;
    private static final int DISCOUNT_AMOUNT_MAX = 10000;
    private static final int DISCOUNT_AMOUNT_UNIT = 500;

    /*
    할인율은 3% 이상, 20% 이하여야 한다.
    할인율은 할인금액 / 최소 주문 금액 식을 사용하여 계산하며, 소수점은 버림 한다.
     */
    private static final int DISCOUNT_RATE_MIN = 3;
    private static final int DISCOUNT_RATE_MAX = 20;

    /*
    최소 주문 금액은 5,000원 이상, 100,000원 이하여야 한다.
     */
    private static final int MINIMUM_ORDER_AMOUNT_MIN = 5000;
    private static final int MINIMUM_ORDER_AMOUNT_MAX = 100000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(30)")
    private String name;

    @Column(name = "discount_amount", columnDefinition = "int")
    private Integer discountAmount;

    @Column(name = "minimum_order_amount", columnDefinition = "int")
    private Integer minimumOrderAmount;

    @Column(name = "category", columnDefinition = "varchar(20)")
    @Enumerated(value = EnumType.STRING)
    private CouponCategory category;

    @Column(name = "issue_started_at", columnDefinition = "datetime")
    private LocalDateTime issueStartedAt;

    @Column(name = "issue_ended_at", columnDefinition = "datetime")
    private LocalDateTime issueEndedAt;

    public Coupon(String name, Integer discountAmount, Integer minimumOrderAmount) {
        validateCoupon(name, discountAmount, minimumOrderAmount);
        this.name = name;
        this.discountAmount = discountAmount;
        this.minimumOrderAmount = minimumOrderAmount;
        this.issueStartedAt = LocalDateTime.now().with(LocalTime.MIN);
        this.issueEndedAt = LocalDateTime.now().plusMonths(EXPIRATION_MONTH - 1).with(LocalTime.MAX);
    }

    private void validateCoupon(String name, Integer discountAmount, Integer minimumOrderAmount) {
        validateName(name);
        validateDiscountAmount(discountAmount);
        validateMinimumOrderAmount(minimumOrderAmount);
        validateDiscountRate(discountAmount, minimumOrderAmount);
    }

    private void validateName(String name) {
        if (name == null || name.strip().isBlank()) {
            throw new IllegalArgumentException("쿠폰 이름은 필수입니다.");
        }
        if (name.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException(String.format("쿠폰 이름은 %d자 이하여야 합니다.", NAME_MAX_LENGTH));
        }
    }

    private void validateDiscountAmount(int discountAmount) {
        if (DISCOUNT_AMOUNT_MIN > discountAmount || discountAmount > DISCOUNT_AMOUNT_MAX) {
            throw new IllegalArgumentException(String.format("할인 금액은 %,d원 이상 %,d원 이하여야 합니다.", DISCOUNT_AMOUNT_MIN,
                    DISCOUNT_AMOUNT_MAX));
        }
        if (discountAmount % DISCOUNT_AMOUNT_UNIT != 0) {
            throw new IllegalArgumentException(String.format("할인 금액은 %,d원 단위로 설정할 수 있습니다.", DISCOUNT_AMOUNT_MIN));
        }
    }

    private void validateMinimumOrderAmount(int minimumOrderAmount) {
        if (MINIMUM_ORDER_AMOUNT_MIN > minimumOrderAmount || minimumOrderAmount > MINIMUM_ORDER_AMOUNT_MAX) {
            throw new IllegalArgumentException(String.format("최소 주문 금액은 %,d원 이상 %,d원 이하여야 합니다.",
                    MINIMUM_ORDER_AMOUNT_MIN, MINIMUM_ORDER_AMOUNT_MAX));
        }
    }

    private void validateDiscountRate(int discountAmount, int minimumOrderAmount) {
        int discountRate = 100 * discountAmount / minimumOrderAmount;
        if (DISCOUNT_RATE_MIN > discountRate || discountRate > DISCOUNT_RATE_MAX) {
            throw new IllegalArgumentException(
                    String.format("할인율은 %d%% 이상 %d%% 이하여야 합니다.", DISCOUNT_RATE_MIN, DISCOUNT_RATE_MAX));
        }
    }

    private void validateIssuePeriod(LocalDateTime issueStartedAt, LocalDateTime issueEndedAt) {
        if (issueStartedAt.isAfter(issueEndedAt)) {
            throw new IllegalArgumentException("발급 시작일은 종료일보다 이전이어야 합니다.");
        }
    }
}
