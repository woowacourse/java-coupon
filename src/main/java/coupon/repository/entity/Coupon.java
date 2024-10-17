package coupon.repository.entity;

import coupon.domain.coupon.CouponAmount;
import coupon.domain.coupon.IssuedDuration;
import coupon.domain.coupon.ProductCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Coupon {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(length = 30)
    private String name; // todo: 30

    @Embedded
    private CouponAmount couponAmount;

    private ProductCategory productCategory;

    @Embedded
    private IssuedDuration issuedDuration;
}
