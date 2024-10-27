package coupon.coupon.domain;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class Coupon {

    private static int MIN_DISCOUNT_RATE = 3;
    private static int MAX_DISCOUNT_RATE = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,
            length = 30)
    private String name;

    @Embedded
    private Discount discount;

    @Embedded
    private Order order;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Embedded
    private IssuableDuration issuableDuration;

    public Coupon(String name, int discount, int order, Category category, LocalDate startDate, LocalDate endDate) {
        validate(discount, order);
        this.name = name;
        this.discount = new Discount(discount);
        this.order = new Order(order);
        this.category = category;
        this.issuableDuration = new IssuableDuration(startDate, endDate);
    }

    private void validate(int discount, int order) {
        int discountRate = calculateDiscountRate(discount, order);
        if (discountRate < MIN_DISCOUNT_RATE || discountRate > MAX_DISCOUNT_RATE) {
            throw new IllegalArgumentException(
                    "Discount rate must be between " + MIN_DISCOUNT_RATE + " and " + MAX_DISCOUNT_RATE
            );
        }
    }

    public int calculateDiscountRate(int discount, int order) {
        return 100 * discount / order;
    }

    public Long getId() {
        return id;
    }
}
