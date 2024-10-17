package coupon;

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
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "coupon")
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponEntity {

    public static CouponEntity from(Coupon coupon) {
        return new CouponEntity(
                null,
                coupon.getName(),
                coupon.getCategory(),
                coupon.getDiscountPrice(),
                coupon.getMinimumOrderPrice(),
                coupon.getIssuanceDate(),
                coupon.getExpirationDate()
        );
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "discount_price", nullable = false)
    private long discountPrice;

    @Column(name = "minimum_order_price", nullable = false)
    private long minimumOrderPrice;

    @Column(name = "issuable_date", nullable = false)
    private LocalDate issuableDate;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;
}
