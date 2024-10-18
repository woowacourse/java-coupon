package coupon.coupon.entity;

import coupon.coupon.domain.Coupon;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Coupon coupon;

    protected CouponEntity() {
    }

    public CouponEntity(Coupon coupon) {
        this.coupon = coupon;
    }

    public static CouponEntity from(Coupon coupon) {
        return new CouponEntity(coupon);
    }
}
