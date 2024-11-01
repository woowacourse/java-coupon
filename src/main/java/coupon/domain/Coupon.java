package coupon.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Entity
public class Coupon implements Serializable {

    private static final BigDecimal LOWEST_ALLOWED_DISCOUNT_RATE = BigDecimal.valueOf(3);
    private static final BigDecimal HIGHEST_ALLOWED_DISCOUNT_RATE = BigDecimal.valueOf(20);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CouponName name;

    @Embedded
    private DiscountAmount discountAmount;

    @Embedded
    private MinimumAmount minimumAmount;

    @Enumerated(EnumType.STRING)
    private Category category;

    private LocalDate startDate;
    private LocalDate endDate;

    protected Coupon() {
    }

    public Coupon(
            String name,
            BigDecimal discountAmount,
            BigDecimal minimumAmount,
            Category category,
            LocalDate startDate,
            LocalDate endDate
    ) {
        this(
                null,
                new CouponName(name),
                new DiscountAmount(discountAmount),
                new MinimumAmount(minimumAmount),
                category,
                startDate,
                endDate
        );
    }

    private Coupon(
            Long id,
            CouponName name,
            DiscountAmount discountAmount,
            MinimumAmount minimumAmount,
            Category category,
            LocalDate startDate,
            LocalDate endDate
    ) {
        validateDiscountRate(discountAmount.getDiscountAmount(), minimumAmount.getMinimumAmount());

        this.id = id;
        this.name = name;
        this.discountAmount = discountAmount;
        this.minimumAmount = minimumAmount;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validateDiscountRate(BigDecimal discountAmount, BigDecimal minimumAmount) {
        BigDecimal discountRate = discountAmount.multiply(BigDecimal.valueOf(100))
                .divide(minimumAmount, RoundingMode.DOWN);
        if (isLowerThanLowestAllowedDiscountRate(discountRate)) {
            throw new IllegalArgumentException("할인율은 3% 이상이어야 합니다.");
        }
        if (isUpperThanHighestAllowedDiscountRate(discountRate)) {
            throw new IllegalArgumentException("할인율은 20% 이하여야 합니다.");
        }
    }

    private boolean isLowerThanLowestAllowedDiscountRate(BigDecimal discountRate) {
        return discountRate.compareTo(LOWEST_ALLOWED_DISCOUNT_RATE) < 0;
    }

    private boolean isUpperThanHighestAllowedDiscountRate(BigDecimal discountRate) {
        return discountRate.compareTo(HIGHEST_ALLOWED_DISCOUNT_RATE) > 0;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount.getDiscountAmount();
    }

    public BigDecimal getMinimumAmount() {
        return minimumAmount.getMinimumAmount();
    }

    public Category getCategory() {
        return category;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
