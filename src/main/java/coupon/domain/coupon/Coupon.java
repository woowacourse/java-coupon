package coupon.domain.coupon;

import coupon.domain.coupon.discount.Discount;
import coupon.domain.coupon.discount.DiscountPolicy;
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
@NoArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CouponName name;

    @Embedded
    private Discount discount;

    private int minOrderPrice;

    @Enumerated(EnumType.STRING)
    private Category category;

    private LocalDate issueStartDate;

    private LocalDate issueEndDate;

    public Coupon(String name, DiscountPolicy discountPolicy, int discountPrice, int minOrderPrice, Category category,
                  LocalDate issueStartDate, LocalDate issueEndDate) {
        discount.validateDiscountPolicy(minOrderPrice);

        this.name = new CouponName(name);
        this.discount = new Discount(discountPrice, discountPolicy);
        this.minOrderPrice = minOrderPrice;
        this.category = category;
        this.issueStartDate = issueStartDate;
        this.issueEndDate = issueEndDate;
    }
}
