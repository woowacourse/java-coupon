package coupon.domain.coupon;

import coupon.domain.coupon.discount.Discount;
import coupon.domain.coupon.discount.DiscountPolicy;
import coupon.domain.coupon.discount.DiscountType;
import coupon.domain.coupon.discount.PercentDiscountPolicy;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CouponName name;

    @Embedded
    private Discount discount;

    @Embedded
    private MinOrderPrice minOrderPrice;

    @Enumerated(EnumType.STRING)
    private Category category;

    private CouponIssueDate couponIssueDate;


    public Coupon(String name, DiscountPolicy discountPolicy, int discountPrice, int minOrderPrice, Category category,
                  LocalDate issueStartDate, LocalDate issueEndDate) {
        this(
                new CouponName(name),
                new Discount(discountPrice, discountPolicy),
                new MinOrderPrice(minOrderPrice),
                category,
                new CouponIssueDate(issueStartDate, issueEndDate)
        );
    }

    public Coupon(CouponName name, Discount discount, MinOrderPrice minOrderPrice, Category category,
                  CouponIssueDate couponIssueDate) {
        discount.validateDiscountPolicy(minOrderPrice.getMinOrderPrice());
        this.name = name;
        this.discount = discount;
        this.minOrderPrice = minOrderPrice;
        this.category = category;
        this.couponIssueDate = couponIssueDate;
    }

    public boolean issueAvailable(LocalDate date) {
        return couponIssueDate.isDateAvailable(date);
    }
}
