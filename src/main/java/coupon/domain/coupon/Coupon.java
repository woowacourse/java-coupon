package coupon.domain.coupon;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @Column(nullable = false)
    private Name name;

    @Embedded
    @Column(nullable = false)
    private DiscountAmount discountAmount;

    @Embedded
    @Column(nullable = false)
    private MinOrderPrice minOrderPrice;

    @Embedded
    @Column(nullable = false)
    private DiscountRate discountRate;

    @Embedded
    @Column(nullable = false)
    private IssuancePeriod issuancePeriod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    protected Coupon() {
    }

    public Coupon(
            Name name,
            DiscountAmount discountAmount,
            MinOrderPrice minOrderPrice,
            DiscountRate discountRate,
            IssuancePeriod issuancePeriod,
            Category category
    ) {
        this.name = name;
        this.discountAmount = discountAmount;
        this.minOrderPrice = minOrderPrice;
        this.discountRate = discountRate;
        this.issuancePeriod = issuancePeriod;
        this.category = category;
    }

    public Coupon(String name, int discountAmount, int minOrderPrice, LocalDate start, LocalDate end, Category category) {
        this.name = new Name(name);
        this.discountAmount = new DiscountAmount(discountAmount);
        this.minOrderPrice = new MinOrderPrice(minOrderPrice);
        this.discountRate = new DiscountRate(this.discountAmount, this.minOrderPrice);
        this.issuancePeriod = new IssuancePeriod(start, end);
        this.category = category;
    }

    public boolean isIssuableDate(LocalDate date) {
        return issuancePeriod.isInRange(date);
    }

    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public DiscountAmount getDiscountAmount() {
        return discountAmount;
    }

    public MinOrderPrice getMinOrderPrice() {
        return minOrderPrice;
    }

    public DiscountRate getDiscountRate() {
        return discountRate;
    }

    public IssuancePeriod getIssuancePeriod() {
        return issuancePeriod;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", name=" + name.getName() +
                '}';
    }
}
