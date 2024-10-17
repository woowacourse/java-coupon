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

import coupon.base.BaseTimeEntity;
import coupon.coupon.domain.Category;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
public class CouponEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "discount_price", nullable = false)
    private Integer discountPrice;

    @Column(name = "minimum_order_price", nullable = false)
    private Integer minimumOrderPrice;

    @Column(name = "discout_price", nullable = false)
    private Integer discountPercent;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "issued_at", nullable = false)
    private LocalDateTime issuedAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    public CouponEntity(String name, int discountPrice, int minimumOrderPrice, Category category) {
        this(null,
                name,
                discountPrice,
                minimumOrderPrice,
                discountPrice / minimumOrderPrice,
                category,
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    public CouponEntity(
            Long id, String name, Integer discountPrice, Integer minimumOrderPrice, Integer discountPercent,
            Category category, LocalDateTime issuedAt, LocalDateTime expiresAt
    ) {
        this.id = id;
        this.name = name;
        this.discountPrice = discountPrice;
        this.minimumOrderPrice = minimumOrderPrice;
        this.discountPercent = discountPercent;
        this.category = category;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }
}
