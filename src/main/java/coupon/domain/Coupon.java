package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

@Entity
@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
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
    @NotBlank
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

    public Coupon(String name, Long discountAmount, Long minimumOrderAmount, Category category,
                  LocalDateTime issueStartDate, LocalDateTime issueEndDate) {
        this.name = name;
        this.discountAmount = discountAmount;
        this.minimumOrderAmount = minimumOrderAmount;
        this.category = category;
        this.issueStartDate = issueStartDate;
        this.issueEndDate = issueEndDate;
        validate();
    }

    public Coupon(String name, Long discountAmount, Long minimumOrderAmount, Category category) {
        this(name, discountAmount, minimumOrderAmount, category, LocalDateTime.now(), LocalDateTime.now());
    }

    public Coupon(Long discountAmount, Long minimumOrderAmount) {
        this("coupon", discountAmount, minimumOrderAmount, Category.FOOD);
    }

    private void validate() {
        Set<ConstraintViolation<Coupon>> violations = validator.validate(this);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Coupon> violation : violations) {
                sb.append(violation.getMessage()).append("\n");
            }
            throw new IllegalArgumentException(sb.toString());
        }
    }

    @AssertTrue(message = "Discount amount must be a multiple of 500")
    public boolean isDiscountAmount() {
        return discountAmount % DISCOUNT_UNIT == 0;
    }

    @AssertTrue(message = "Discount rate must be between 3 and 20")
    public boolean isDiscountRate() {
        double discountRate = Math.floor(discountAmount * 1.0 / minimumOrderAmount * 100);
        return discountRate >= MIN_DISCOUNT_RATE && discountRate <= MAX_DISCOUNT_RATE;
    }

    @AssertTrue(message = "Issue date cannot be null")
    public boolean isIssDateNotNull() {
        return issueStartDate != null && issueEndDate != null;
    }

    @AssertTrue(message = "Issue date cannot be after issue date")
    public boolean isIssueBeforeDate() {
        return !issueStartDate.isAfter(issueEndDate);
    }
}
