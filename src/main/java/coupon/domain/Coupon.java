package coupon.domain;

import coupon.domain.exception.CouponConditionException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Coupon {

    private static final int MAX_NAME_LENGTH = 30;

    private static final int MIN_MIN_ORDER_AMOUNT = 5_000;
    private static final int MAX_MIN_ORDER_AMOUNT = 100_000;

    private static final int MIN_DISCOUNT_AMOUNT = 1_000;
    private static final int MAX_DISCOUNT_AMOUNT = 10_000;
    private static final int DISCOUNT_AMOUNT_UNIT = 500;
    private static final int MIN_DISCOUNT_RATE = 3;
    private static final int MAX_DISCOUNT_RATE = 20;

    private static final LocalTime START_ISSUE_TIME = LocalTime.MIN;
    private static final LocalTime END_ISSUE_TIME = LocalTime.MAX;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int minOrderAmount;

    @Column(nullable = false)
    private int discountAmount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private LocalDateTime startIssueAt;

    @Column(nullable = false)
    private LocalDateTime endIssueAt;

    public Coupon(String name,
                  int discountAmount,
                  int minOrderAmount,
                  Category category,
                  LocalDate startIssueDate,
                  LocalDate endIssueDate) {
        validateName(name);
        validateMinOrderAmount(minOrderAmount);
        validateDiscountAmount(discountAmount, minOrderAmount);
        validateCategory(category);
        validateIssueDate(startIssueDate, endIssueDate);

        this.name = name;
        this.discountAmount = discountAmount;
        this.minOrderAmount = minOrderAmount;
        this.category = category;
        this.startIssueAt = startIssueDate.atTime(START_ISSUE_TIME);
        this.endIssueAt = endIssueDate.atTime(END_ISSUE_TIME);
    }

    private void validateName(String name) {
        if (Objects.isNull(name) || name.isBlank() || name.length() > MAX_NAME_LENGTH) {
            throw new CouponConditionException("Coupon name must not be null and must be less than limit.");
        }
    }

    private void validateMinOrderAmount(int minOrderAmount) {
        if (minOrderAmount < MIN_MIN_ORDER_AMOUNT || minOrderAmount > MAX_MIN_ORDER_AMOUNT) {
            throw new CouponConditionException("Minimum order amount must be in bound.");
        }
    }

    private void validateDiscountAmount(int discountAmount, int minOrderAmount) {
        if (discountAmount < MIN_DISCOUNT_AMOUNT || discountAmount > MAX_DISCOUNT_AMOUNT
                || discountAmount % DISCOUNT_AMOUNT_UNIT != 0) {
            throw new CouponConditionException("Discount amount must be in bound and multiple of unit.");
        }

        int discountRate = discountAmount * 100 / minOrderAmount;
        if (discountRate < MIN_DISCOUNT_RATE || discountRate > MAX_DISCOUNT_RATE) {
            throw new CouponConditionException("Discount rate must be in bound.");
        }
    }

    private void validateCategory(Category category) {
        if (category == null) {
            throw new CouponConditionException("Category must not be null.");
        }
    }

    private void validateIssueDate(LocalDate startIssueDate, LocalDate endIssueDate) {
        if (startIssueDate == null || endIssueDate == null || startIssueDate.isAfter(endIssueDate)) {
            throw new CouponConditionException("Start issue date must be before or equal to end issue date.");
        }
    }
}
