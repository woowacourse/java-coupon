package coupon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    private static final int DISCOUNT_AMOUNT_UNIT = 500;
    private static final double MIN_DISCOUNT_RATE = 0.03;
    private static final double MAX_DISCOUNT_RATE = 0.2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(min = 1, max = 30)
    @NotNull
    private String name;

    @Min(1000)
    @Max(10000)
    @NotNull
    private Integer discountAmt;

    @Min(5000)
    @Max(100000)
    @NotNull
    private Integer minOrderAmt;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CouponCategory category;

    @NotNull
    private LocalDateTime issuedStartDate;
    
    @NotNull
    private LocalDateTime issuedEndDate;

    public Coupon(
            String name,
            int discountAmt,
            int minOrderAmt,
            CouponCategory category,
            LocalDateTime issuedStartDate,
            LocalDateTime issuedEndDate
    ) {
        this.name = name;
        this.discountAmt = discountAmt;
        this.minOrderAmt = minOrderAmt;
        this.category = category;
        this.issuedStartDate = issuedStartDate;
        this.issuedEndDate = issuedEndDate;
    }

    @AssertTrue
    public boolean validateDiscountAmtUnit() {
        return discountAmt % DISCOUNT_AMOUNT_UNIT == 0;
    }

    @AssertTrue
    public boolean validateDiscountRate() {
        double rate = discountAmt / (double) minOrderAmt;
        return rate >= MIN_DISCOUNT_RATE && rate <= MAX_DISCOUNT_RATE;
    }

    @AssertTrue
    public boolean validateIssuedStartDate() {
        return !issuedStartDate.isAfter(issuedEndDate);
    }
}
