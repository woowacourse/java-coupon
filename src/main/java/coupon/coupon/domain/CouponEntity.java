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

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    public CouponEntity(String couponName, int couponDiscountAmount, int couponMinOrderAmount,
                        CouponCategory couponCategory,
                        LocalDateTime couponStartAt, LocalDateTime couponEndAt) {
        this.couponName = couponName;
        this.couponDiscountAmount = couponDiscountAmount;
        this.couponMinOrderAmount = couponMinOrderAmount;
        this.couponCategory = couponCategory;
        this.startAt = couponStartAt;
        this.endAt = couponEndAt;
    }

    public static CouponEntity mapToEntity(Coupon domain) {
        return new CouponEntity(
                domain.getCouponName().getName(),
                domain.getCouponDiscountAmount(),
                domain.getCouponMinOrderAmount(),
                domain.getCouponCategory(),
                domain.getCouponPeriod().getStartAt(),
                domain.getCouponPeriod().getEndAt()
        );
    }
}
