package coupon.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
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

    private BigDecimal discountAmount;

    @Embedded
    private MinimumAmount minimumAmount;
    private String category;
    private LocalDate startDate;
    private LocalDate endDate;

    protected Coupon() {
    }

    public Coupon(
            String name,
            BigDecimal discountAmount,
            BigDecimal minimumAmount,
            String category,
            LocalDate startDate,
            LocalDate endDate
    ) {
        this(
                null,
                new CouponName(name),
                discountAmount,
                new MinimumAmount(minimumAmount),
                category,
                startDate,
                endDate
        );
    }

    private Coupon(
            Long id,
            CouponName name,
            BigDecimal discountAmount,
            MinimumAmount minimumAmount,
            String category,
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
        return discountAmount;
    }

    public BigDecimal getMinimumAmount() {
        return minimumAmount.getMinimumAmount();
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
