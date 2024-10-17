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
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Coupon {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(length = 30)
    private String name; // todo: 30

    private ProductCategory productCategory;

    @Embedded
    private CouponAmount couponAmount;

    @Embedded
    private IssuedDuration issuedDuration;

    public Coupon(String name, Integer discountAmount, Integer minOrderAmount,
                  ProductCategory productCategory,
                  LocalDateTime issueStartedAt, LocalDateTime issueEndedAt) {
        this.name = name;
        this.productCategory = productCategory;
        this.couponAmount = new CouponAmount(discountAmount, minOrderAmount);
        this.issuedDuration = new IssuedDuration(issueStartedAt, issueEndedAt);
    }
}
