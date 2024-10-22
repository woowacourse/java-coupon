package coupon.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
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

    private Long id;
    private String name;
    private Integer discountAmount;
    private Integer minimumOrderAmount;
    private CouponCategory category;
    private LocalDateTime issueStartedAt;
    private LocalDateTime issueEndedAt;

    public Coupon(String name, Integer discountAmount, Integer minimumOrderAmount) {
        validateCoupon(name, discountAmount, minimumOrderAmount);
        this.name = name;
        this.discountAmount = discountAmount;
        this.minimumOrderAmount = minimumOrderAmount;
        this.issueStartedAt = LocalDateTime.now().with(LocalTime.MIN);
        this.issueEndedAt = LocalDateTime.now().plusMonths(EXPIRATION_MONTH).minusDays(1).with(LocalTime.MAX)
                .truncatedTo(ChronoUnit.MICROS);
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
            throw new IllegalArgumentException(String.format("할인 금액은 %,d원 단위로 설정할 수 있습니다.", DISCOUNT_AMOUNT_UNIT));
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
