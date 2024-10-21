package coupon.coupon.domain;

import coupon.BaseEntity;
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
@Table(name = "coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseEntity {

    public static final int MAX_NAME_LENGTH = 30;
    public static final int MIN_DISCOUNT_AMOUNT = 1_000;
    public static final int MAX_DISCOUNT_AMOUNT = 10_000;
    public static final int DISCOUNT_AMOUNT_UNIT = 500;
    public static final int MIN_MINIMUM_ORDER_AMOUNT = 5_000;
    public static final int MAX_MINIMUM_ORDER_AMOUNT = 100_000;
    public static final int MIN_DISCOUNT_RATE = 3;
    public static final int MAX_DISCOUNT_RATE = 20;

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
        if (name == null || name.isBlank() || name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(String.format(
                    ExceptionMessage.NAME_LENGTH_EXCEPTION.getMessage(),
                    MAX_NAME_LENGTH
            ));
        }
    }

    private static void validateDiscountAmount(int discountAmount) {
        if (discountAmount < MIN_DISCOUNT_AMOUNT
                || discountAmount > MAX_DISCOUNT_AMOUNT
                || discountAmount % DISCOUNT_AMOUNT_UNIT != 0) {
            throw new IllegalArgumentException(String.format(
                    ExceptionMessage.DISCOUNT_AMOUNT_EXCEPTION.getMessage(),
                    MIN_DISCOUNT_AMOUNT,
                    MAX_DISCOUNT_AMOUNT,
                    DISCOUNT_AMOUNT_UNIT
            ));
        }
    }

    private static void validateMinimumOrderAmount(int minimumOrderAmount) {
        if (minimumOrderAmount < MIN_MINIMUM_ORDER_AMOUNT || minimumOrderAmount > MAX_MINIMUM_ORDER_AMOUNT) {
            throw new IllegalArgumentException(String.format(
                    ExceptionMessage.MINIMUM_ORDER_AMOUNT_EXCEPTION.getMessage(),
                    MIN_MINIMUM_ORDER_AMOUNT,
                    MAX_MINIMUM_ORDER_AMOUNT
            ));
        }
    }

    private static void validateDiscountRate(int discountAmount, int minimumOrderAmount) {
        double discountRate = Math.floor((double) discountAmount / minimumOrderAmount * 100);
        if (discountRate < MIN_DISCOUNT_RATE || discountRate > MAX_DISCOUNT_RATE) {
            throw new IllegalArgumentException(String.format(
                    ExceptionMessage.DISCOUNT_RATE_EXCEPTION.getMessage(),
                    MIN_DISCOUNT_RATE,
                    MAX_DISCOUNT_RATE
            ));
        }
    }

    private static void validateEndDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(ExceptionMessage.START_DATE_BEFORE_END_DATE_EXCEPTION.getMessage());
        }
    }
}
