package coupon.coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private CouponName couponName;

    @Embedded
    private CouponDiscountAmount couponDiscountAmount;

    @Embedded
    private CouponMinOrderAmount couponMinOrderAmount;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private CouponCategory couponCategory;

    @Embedded
    private CouponPeriod couponPeriod;

    public Coupon(String couponName, int couponDiscountAmount, int couponMinOrderAmount, String couponCategory,
                  LocalDateTime couponStartDate, LocalDateTime couponEndDate) {
        this.couponName = new CouponName(couponName);
        this.couponDiscountAmount = new CouponDiscountAmount(couponDiscountAmount, couponMinOrderAmount);
        this.couponMinOrderAmount = new CouponMinOrderAmount(couponMinOrderAmount);
        this.couponCategory = CouponCategory.valueOf(couponCategory);
        this.couponPeriod = new CouponPeriod(couponStartDate, couponEndDate);
    }
}
