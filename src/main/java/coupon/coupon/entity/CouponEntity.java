package coupon.coupon.entity;

import coupon.BaseEntity;
import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponName;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "coupon")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = CouponName.MAX_NAME_LENGTH)
    private String name;

    @Column(name = "discount_amount", nullable = false)
    private int discountAmount;

    @Column(name = "minimum_order_amount", nullable = false)
    private int minimumOrderAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    public CouponEntity(Coupon coupon) {
        this(null, coupon.getName(), coupon.getDiscountAmount(), coupon.getMinimumOrderAmount(), coupon.getCategory(), coupon.getStartDate(), coupon.getEndDate());
    }
}
