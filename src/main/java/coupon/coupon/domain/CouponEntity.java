package coupon.coupon.domain;

import jakarta.persistence.Column;
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
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String couponName;

    @Column(name = "discount_amount", nullable = false)
    private int couponDiscountAmount;

    @Column(name = "min_order_amount", nullable = false)
    private int couponMinOrderAmount;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private CouponCategory couponCategory;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    public CouponEntity(String couponName, int couponDiscountAmount, int couponMinOrderAmount,
                        CouponCategory couponCategory,
                        LocalDateTime couponStartDate, LocalDateTime couponEndDate) {
        this.couponName = couponName;
        this.couponDiscountAmount = couponDiscountAmount;
        this.couponMinOrderAmount = couponMinOrderAmount;
        this.couponCategory = couponCategory;
        this.startDate = couponStartDate;
        this.endDate = couponEndDate;
    }

    public static CouponEntity mapDomainToEntity(Coupon domain) {
        return new CouponEntity(
                domain.getCouponName().getName(),
                domain.getCouponDiscountAmount().getAmount(),
                domain.getCouponMinOrderAmount().getAmount(),
                domain.getCouponCategory(),
                domain.getCouponPeriod().getStartDate(),
                domain.getCouponPeriod().getEndDate()
        );
    }

    public Coupon mapEntityToDomain() {
        return new Coupon(couponName, couponDiscountAmount, couponMinOrderAmount, couponCategory,
                startDate, endDate);
    }
}
