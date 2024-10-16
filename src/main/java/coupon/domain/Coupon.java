package coupon.domain;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Coupon {

    private static final int MAX_NAME_LENGTH = 30;
    private static final int MIN_DISCOUNT_AMOUNT = 1_000;
    private static final int MAX_DISCOUNT_AMOUNT = 10_000;
    private static final int DISCOUNT_AMOUNT_INCREMENT = 500;
    private static final int MIN_MIN_ORDER_AMOUNT = 5_000;
    private static final int MAX_MIN_ORDER_AMOUNT = 100_000;
    private static final int MIN_DISCOUNT_RATE = 3;
    private static final int MAX_DISCOUNT_RATE = 20;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true)
    private int discountAmount;

    @Column(nullable = true)
    private int minOrderAmount;

    @Enumerated(value = STRING)
    private CouponCategory category;

    @Column(nullable = true)
    private LocalDate startDate;

    @Column(nullable = true)
    private LocalDate endDate;

    public Coupon(
            String name, int discountAmount, int minOrderAmount, String category,
            LocalDate startDate, LocalDate endDate
    ) {
        validateName(name);
        validateDiscountAmount(discountAmount);
        validateMinOrderAmount(minOrderAmount);
        validateDiscountRate(discountAmount, minOrderAmount);
        validateDuration(startDate, endDate);
        this.name = name;
        this.discountAmount = discountAmount;
        this.minOrderAmount = minOrderAmount;
        this.category = CouponCategory.from(category);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name must exist and not be empty.");
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Name length must be " + MAX_NAME_LENGTH + " characters or less.");
        }
    }

    private void validateDiscountAmount(int discountAmount) {
        if (discountAmount < MIN_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException("Discount amount must be at least " + MIN_DISCOUNT_AMOUNT + " won.");
        }
        if (discountAmount > MAX_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException("Discount amount must be at most " + MAX_DISCOUNT_AMOUNT + " won.");
        }
        if (discountAmount % DISCOUNT_AMOUNT_INCREMENT != 0) {
            throw new IllegalArgumentException("Discount amount must be in increments of " + DISCOUNT_AMOUNT_INCREMENT + " won.");
        }
    }

    private void validateMinOrderAmount(int minOrderAmount) {
        if (minOrderAmount < MIN_MIN_ORDER_AMOUNT) {
            throw new IllegalArgumentException("Minimum order amount must be at least " + MIN_MIN_ORDER_AMOUNT + " won.");
        }
        if (minOrderAmount > MAX_MIN_ORDER_AMOUNT) {
            throw new IllegalArgumentException("Minimum order amount must be at most " + MAX_MIN_ORDER_AMOUNT + " won.");
        }
    }

    private void validateDiscountRate(int discountAmount, int minOrderAmount) {
        int discountRate = (discountAmount * 100) / minOrderAmount; // Integer division floors the decimal
        if (discountRate < MIN_DISCOUNT_RATE) {
            throw new IllegalArgumentException("Discount rate must be at least " + MIN_DISCOUNT_RATE + "%.");
        }
        if (discountRate > MAX_DISCOUNT_RATE) {
            throw new IllegalArgumentException("Discount rate must be at most " + MAX_DISCOUNT_RATE + "%.");
        }
    }

    private void validateDuration(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date must not be null.");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date.");
        }
    }
}
