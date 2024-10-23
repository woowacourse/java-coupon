package coupon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    private static final Validator validator;

    static {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Length(min = 1, max = 30, message = "쿠폰명은 최소 1자 이상 최대 30자 이하여야 합니다.")
    private String name;

    @DecimalMin(value = "1000", message = "할인 금액은 1,000원 이상이어야 합니다.")
    @DecimalMax(value = "10000", message = "할인 금액은 10,000원 이하여야 합니다.")
    private BigDecimal discountAmount;

    @DecimalMin(value = "5000", message = "최소 주문 금액은 5,000원 이상이어야 합니다.")
    @DecimalMax(value = "100000", message = "최소 주문 금액은 100,000원 이하여야 합니다.")
    private BigDecimal minimumOrderAmount;

    @Enumerated(EnumType.STRING)
    private CouponCategory category;

    private LocalDateTime issuedStartDate;

    private LocalDateTime issuedEndDate;

    public Coupon(
            String name,
            BigDecimal discountAmount,
            BigDecimal minimumOrderAmount,
            CouponCategory category,
            LocalDateTime issuedStartDate,
            LocalDateTime issuedEndDate
    ) {
        this.name = name;
        this.discountAmount = discountAmount;
        this.minimumOrderAmount = minimumOrderAmount;
        this.category = category;
        this.issuedStartDate = issuedStartDate;
        this.issuedEndDate = issuedEndDate;

        validate();
    }

    public boolean issueAvailable() {
        if (issuedStartDate == null || issuedEndDate == null) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        if (issuedStartDate.toLocalDate() == issuedEndDate.toLocalDate()) {
            return now.toLocalDate().equals(issuedStartDate.toLocalDate());
        }

        return !now.isBefore(issuedStartDate) && !now.isAfter(issuedEndDate);
    }

    private void validate() {
        Set<ConstraintViolation<Coupon>> violations = validator.validate(this);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    @AssertTrue(message = "할인 금액은 500원 단위여야 합니다.")
    public boolean isValidDiscountAmountUnit() {
        return discountAmount.remainder(BigDecimal.valueOf(500)).compareTo(BigDecimal.ZERO) == 0;
    }

    @AssertTrue(message = "할인율은 3% 이상 20% 이하여야 합니다.")
    public boolean isValidDiscountRate() {
        BigDecimal ratio = discountAmount.divide(minimumOrderAmount, 2, RoundingMode.DOWN);
        return ratio.compareTo(BigDecimal.valueOf(0.03)) >= 0 && ratio.compareTo(BigDecimal.valueOf(0.2)) <= 0;
    }

    @AssertTrue(message = "발급 기간 시작일은 종료일보다 이전이거나 같아야 합니다.")
    public boolean isValidIssuedStartDate() {
        return !issuedStartDate.isAfter(issuedEndDate);
    }
}
