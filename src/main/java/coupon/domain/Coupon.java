package coupon.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Coupon {

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
        this.id = id;
        this.name = name;
        this.discountAmount = discountAmount;
        this.minimumAmount = minimumAmount;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
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
