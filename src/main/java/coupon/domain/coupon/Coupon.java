package coupon.domain.coupon;

import java.time.LocalDate;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "coupon_name"))
    private CouponName couponName;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "startDate", column = @Column(name = "start_date")),
            @AttributeOverride(name = "endDate", column = @Column(name = "end_date"))
    })
    private CouponDuration couponDuration;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "order_amount"))
    private OrderAmount orderAmount;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "discount_amount"))
    private DiscountAmount discountAmount;

    public Coupon(String couponName,
                  Category category,
                  LocalDate startDate,
                  LocalDate endDate,
                  int orderAmount,
                  int discountAmount
    ) {
        this.couponName = new CouponName(couponName);
        this.category = category;
        this.couponDuration = new CouponDuration(startDate, endDate);
        validate(discountAmount, orderAmount);
        this.orderAmount = new OrderAmount(orderAmount);
        this.discountAmount = new DiscountAmount(discountAmount);
    }

    private void validate(int discountAmount, int orderAmount) {
        double discountRate = (double) orderAmount / discountAmount;
        if (discountRate < 3 || discountRate > 20) {
            throw new IllegalArgumentException("할인율은 3% 이상 20% 이하만 가능합니다.");
        }
    }
}
