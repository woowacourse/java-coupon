package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import org.hibernate.validator.constraints.Range;

@Entity
public class Coupon {

    private static final int MAX_NAME_LENGTH = 30;
    private static final int MIN_DISCOUNT_AMOUNT = 1000;
    private static final int MAX_DISCOUNT_AMOUNT = 10000;
    private static final int DISCOUNT_UNIT = 500;
    private static final int MIN_DISCOUNT_RATE = 3;
    private static final int MAX_DISCOUNT_RATE = 20;
    private static final int MIN_ORDER_AMOUNT = 5_000;
    private static final int MAX_ORDER_AMOUNT = 100_000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(max = MAX_NAME_LENGTH)
    private String name;

    @Column(nullable = false)
    @Range(min = MIN_DISCOUNT_AMOUNT, max = MAX_DISCOUNT_AMOUNT)
    private Long discountAmount;

    @Column(nullable = false)
    @Range(min = MIN_ORDER_AMOUNT, max = MAX_ORDER_AMOUNT)
    private Long minimumOrderAmount;

    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private LocalDateTime issueStartDate;

    @Column(nullable = false)
    private LocalDateTime issueEndDate;

    protected Coupon() {
    }

    public Coupon(String name, Long discountAmount, Long minimumOrderAmount, Category category,
                  LocalDateTime issueStartDate, LocalDateTime issueEndDate) {
        validateDiscountAmount(discountAmount);
        validateIssueDate(issueStartDate, issueEndDate);
        this.name = name;
        this.discountAmount = discountAmount;
        this.minimumOrderAmount = minimumOrderAmount;
        this.category = category;
        this.issueStartDate = issueStartDate;
        this.issueEndDate = handleSameDate(issueStartDate, issueEndDate);
    }

    private void validateDiscountAmount(Long discountAmount) {
        if (discountAmount % DISCOUNT_UNIT != 0) {
            throw new IllegalArgumentException("Discount amount must be a multiple of 500");
        }
        double discountRate = Math.floor(discountAmount * 1.0 / minimumOrderAmount);
        if (discountRate < MIN_DISCOUNT_RATE || discountRate > MAX_DISCOUNT_RATE) {
            throw new IllegalArgumentException("Discount rate must be between 3 and 20");
        }
    }

    private void validateIssueDate(LocalDateTime issueStartDate, LocalDateTime issueEndDate) {
        if (issueStartDate == null || issueEndDate == null) {
            throw new IllegalArgumentException("Issue date cannot be null");
        }
        if (issueStartDate.isAfter(issueEndDate)) {
            throw new IllegalArgumentException("Issue date cannot be after issue date");
        }
    }
    private LocalDateTime handleSameDate(LocalDateTime issueStartDate, LocalDateTime issueEndDate) {
        if (issueStartDate.isEqual(issueEndDate)) {
            return LocalDateTime.of(issueStartDate.getYear(),
                    issueStartDate.getMonth(),
                    issueStartDate.getDayOfMonth(),
                    23,
                    59,
                    59,
                    999999
            );
        }
        return issueEndDate;
    }
}
