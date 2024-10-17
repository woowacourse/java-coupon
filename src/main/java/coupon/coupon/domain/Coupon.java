package coupon.coupon.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import coupon.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
public class Coupon extends BaseTimeEntity {

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

    @JoinColumn(name = "category")
    @ManyToOne(optional = false)
    private Category category;

    @Column(name = "issued_at", nullable = false)
    private LocalDateTime issuedAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    public Coupon(String name, int discountPrice, int minimumOrderPrice, Category category) {
        this(null,
                name,
                discountPrice,
                minimumOrderPrice,
                discountPrice / minimumOrderPrice,
                category,
                LocalDateTime.now(),
                LocalDateTime.now());
    }
}
