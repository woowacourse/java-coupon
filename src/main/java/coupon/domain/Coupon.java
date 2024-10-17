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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@Table(name = "coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    private static final int MAX_NAME_LENGTH = 30;
    private static final int MIN_DISCOUNT_AMOUNT = 1000;
    private static final int MAX_DISCOUNT_AMOUNT = 10000;
    private static final int DISCOUNT_AMOUNT_UNIT = 500;
    private static final int MIN_MINIMUM_ORDER_AMOUNT = 5000;
    private static final int MAX_MINIMUM_ORDER_AMOUNT = 100000;
    private static final int MIN_DISCOUNT_RATE = 3;
    private static final int MAX_DISCOUNT_RATE = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = MAX_NAME_LENGTH)
    private String name;

    @Column(name = "discount_amount", nullable = false)
    private int discountAmount;

    @Column(name = "minimum_order_amount", nullable = false)
    private int minimumOrderAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    public Coupon(
            String name,
            int discountAmount,
            int minimumOrderAmount,
            Category category,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        validate(name, discountAmount, minimumOrderAmount, startDate, endDate);
        this.name = name;
        this.discountAmount = discountAmount;
        this.minimumOrderAmount = minimumOrderAmount;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static void validate(
            String name,
            int discountAmount,
            int minimumOrderAmount,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        validateName(name);
        validateDiscountAmount(discountAmount);
        validateMinimumOrderAmount(minimumOrderAmount);
        validateDiscountRate(discountAmount, minimumOrderAmount);
        validateEndDate(startDate, endDate);
    }

    private static void validateName(String name) {
        if (name == null || name.isEmpty() || name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(String.format(
                    "이름은 반드시 존재해야 하며, 최대 %d자 이하여야 합니다",
                    MAX_NAME_LENGTH
            ));
        }
    }

    private static void validateDiscountAmount(int discountAmount) {
        if (discountAmount < MIN_DISCOUNT_AMOUNT
                || discountAmount > MAX_DISCOUNT_AMOUNT
                || discountAmount % DISCOUNT_AMOUNT_UNIT != 0) {
            throw new IllegalArgumentException(String.format(
                    "할인 금액은 %d원 이상, %d원 이하이며 %d원 단위로 설정해야 합니다",
                    MIN_DISCOUNT_AMOUNT,
                    MAX_DISCOUNT_AMOUNT,
                    DISCOUNT_AMOUNT_UNIT
            ));
        }
    }

    private static void validateMinimumOrderAmount(int minimumOrderAmount) {
        if (minimumOrderAmount < MIN_MINIMUM_ORDER_AMOUNT || minimumOrderAmount > MAX_MINIMUM_ORDER_AMOUNT) {
            throw new IllegalArgumentException(String.format(
                    "최소 주문 금액은 %d원 이상, %d원 이하여야 합니다",
                    MIN_MINIMUM_ORDER_AMOUNT,
                    MAX_MINIMUM_ORDER_AMOUNT
            ));
        }
    }

    private static void validateDiscountRate(int discountAmount, int minimumOrderAmount) {
        double discountRate = Math.floor((double) discountAmount / minimumOrderAmount * 100);
        if (discountRate < MIN_DISCOUNT_RATE || discountRate > MAX_DISCOUNT_RATE) {
            throw new IllegalArgumentException(String.format(
                    "할인율은 %d%% 이상, %d%% 이하여야 합니다",
                    MIN_DISCOUNT_RATE,
                    MAX_DISCOUNT_RATE
            ));
        }
    }

    private static void validateEndDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작일은 종료일보다 이전이어야 합니다");
        }
    }
}
