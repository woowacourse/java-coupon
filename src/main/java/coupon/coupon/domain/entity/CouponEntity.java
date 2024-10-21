package coupon.coupon.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "discount_price", nullable = false)
    private Integer discountPrice;

    @Column(name = "minimum_order_price", nullable = false)
    private Integer minimumOrderPrice;

    @Column(name = "discount_percent", nullable = false)
    private Double discountPercent;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "issued_at", nullable = false)
    private LocalDateTime issuedAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    public CouponEntity(Coupon coupon) {
        this(null,
                coupon.getName(),
                coupon.getDiscountPrice(),
                coupon.getMinimumOrderPrice(),
                coupon.getDiscountPercent(),
                coupon.getCategory(),
                coupon.getIssuedAt(),
                coupon.getExpiresAt());
    }
}
