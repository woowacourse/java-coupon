package coupon.domain;

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
    private String name;
    private BigDecimal discountAmount;
    private BigDecimal discountRate;
    private BigDecimal minimumAmount;
    private String category;
    private LocalDate startDate;
    private LocalDate endDate;

    protected Coupon() {
    }

    public Coupon(
            String name,
            BigDecimal discountAmount,
            BigDecimal discountRate,
            BigDecimal minimumAmount,
            String category,
            LocalDate startDate,
            LocalDate endDate
    ) {
        this(null, name, discountAmount, discountRate, minimumAmount, category, startDate, endDate);
    }

    private Coupon(
            Long id,
            String name,
            BigDecimal discountAmount,
            BigDecimal discountRate,
            BigDecimal minimumAmount,
            String category,
            LocalDate startDate,
            LocalDate endDate
    ) {
        this.id = id;
        this.name = name;
        this.discountAmount = discountAmount;
        this.discountRate = discountRate;
        this.minimumAmount = minimumAmount;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public BigDecimal getMinimumAmount() {
        return minimumAmount;
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
