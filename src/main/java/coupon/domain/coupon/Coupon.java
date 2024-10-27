package coupon.domain.coupon;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.data.redis.core.RedisHash;

@Entity
@RedisHash(value = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "coupon_name", nullable = false))
    private CouponName name;

    @Embedded
    @AttributeOverride(name = "price", column = @Column(name = "discount_price", nullable = false))
    private DiscountPrice discountPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Embedded
    @AttributeOverride(name = "price", column = @Column(name = "sale_order_price", nullable = false))
    private SaleOrderPrice saleOrderPrice;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "startAt", column = @Column(name = "start_at", nullable = false)),
            @AttributeOverride(name = "endAt", column = @Column(name = "end_at", nullable = false))
    })
    private IssueDuration duration;

    public Coupon(
            String name,
            int salePrice,
            Category category,
            int orderPrice,
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {
        this.id = null;
        this.name = new CouponName(name);
        this.discountPrice = new DiscountPrice(salePrice);
        this.category = category;
        this.saleOrderPrice = new SaleOrderPrice(orderPrice);
        this.duration = new IssueDuration(startTime, endTime);
        DiscountRatio.validateDiscountRatio(100 * salePrice / orderPrice);
    }

    protected Coupon() {
    }

    public boolean isBetweenIssueDuration(LocalDateTime issuedAt) {
        return duration.isBetween(issuedAt);
    }

    public Long getId() {
        return id;
    }

    public DiscountRatio getDiscountRatio() {
        return new DiscountRatio(100 * discountPrice.getPrice() / saleOrderPrice.getPrice());
    }
}
