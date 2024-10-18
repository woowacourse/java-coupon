package coupon.entity;

import coupon.domain.Coupon;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coupon")
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int discountAmount;
    private int minimumOrderPrice;

    public CouponEntity(int discountAmount, int minimumOrderPrice) {
        this.discountAmount = discountAmount;
        this.minimumOrderPrice = minimumOrderPrice;
    }

    public Coupon toCoupon() {
        return new Coupon(discountAmount, minimumOrderPrice);
    }

    public Long getId() {
        return id;
    }
}
