package coupon.coupon.repository;

import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.DiscountMoney;
import coupon.coupon.domain.DiscountRate;
import coupon.coupon.domain.IssuedPeriod;
import coupon.coupon.domain.Name;
import coupon.coupon.domain.OrderPrice;
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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coupon")
@Entity
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "discount_money", nullable = false)
    private Long discountMoney;

    @Column(name = "discount_rate", nullable = false)
    private Long discountRate;

    @Column(name = "order_price", nullable = false)
    private Long orderPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "start", nullable = false)
    private LocalDateTime start;

    @Column(name = "end", nullable = false)
    private LocalDateTime end;

    public CouponEntity(Coupon coupon) {
        this(null, coupon.getName(),
                coupon.getDiscountMoney(), coupon.getDiscountRate(), coupon.getOrderPrice(), coupon.getCategory(),
                coupon.getStart(), coupon.getEnd());
    }

    public Coupon toDomain() {
        return new Coupon(
                new Name(name),
                new DiscountMoney(discountMoney),
                new DiscountRate(discountRate),
                new OrderPrice(orderPrice),
                category,
                new IssuedPeriod(start, end)
        );
    }
}
