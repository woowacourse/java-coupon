package coupon.repository;

import coupon.domain.Category;
import coupon.domain.Coupon;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coupon")
@Entity
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long discountMoney;

    @Column(nullable = false)
    private Long discountRate;

    @Column(nullable = false)
    private Long orderPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private LocalDateTime start;

    @Column(nullable = false)
    private LocalDateTime end;

    public CouponEntity(Coupon coupon) {
        this(null, coupon.getName(),
                coupon.getDiscountMoney(), coupon.getDiscountRate(), coupon.getOrderPrice(), coupon.getCategory(),
                coupon.getStart(), coupon.getEnd());
    }
}
