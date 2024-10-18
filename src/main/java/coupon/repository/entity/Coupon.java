package coupon.repository.entity;

import coupon.domain.coupon.CouponAmount;
import coupon.domain.coupon.CouponName;
import coupon.domain.coupon.IssuedDuration;
import coupon.domain.coupon.ProductCategory;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CouponName name;

    private ProductCategory productCategory;

    @Embedded
    private CouponAmount couponAmount;

    @Embedded
    private IssuedDuration issuedDuration;

    public Coupon(String name,
                  Integer discountAmount, Integer minOrderAmount,
                  ProductCategory productCategory,
                  LocalDateTime issueStartedAt, LocalDateTime issueEndedAt) {
        this.name = new CouponName(name);
        this.productCategory = productCategory;
        this.couponAmount = new CouponAmount(discountAmount, minOrderAmount);
        this.issuedDuration = new IssuedDuration(issueStartedAt, issueEndedAt);
    }
}
