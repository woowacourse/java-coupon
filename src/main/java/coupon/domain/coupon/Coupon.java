package coupon.domain.coupon;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

@Entity
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
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

    public Coupon(Long id,
                  CouponName name,
                  DiscountPrice discountPrice,
                  Category category,
                  SaleOrderPrice saleOrderPrice,
                  IssueDuration duration
    ) {
        DiscountRatio.validateDiscountRatio(100 * discountPrice.getPrice() / saleOrderPrice.getPrice());
        this.id = id;
        this.name = name;
        this.discountPrice = discountPrice;
        this.category = category;
        this.saleOrderPrice = saleOrderPrice;
        this.duration = duration;
    }

    public Coupon(
            String name,
            int salePrice,
            Category category,
            int orderPrice,
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {
        this(null, new CouponName(name), new DiscountPrice(salePrice), category, new SaleOrderPrice(orderPrice), new IssueDuration(startTime, endTime));
    }

    protected Coupon() {
    }

    public boolean isBetweenIssueDuration(LocalDateTime issuedAt) {
        return duration.isBetween(issuedAt);
    }

    @JsonIgnore
    public DiscountRatio getDiscountRatio() {
        return new DiscountRatio(100 * discountPrice.getPrice() / saleOrderPrice.getPrice());
    }

    public Long getId() {
        return id;
    }

    public CouponName getName() {
        return name;
    }

    public DiscountPrice getDiscountPrice() {
        return discountPrice;
    }

    public Category getCategory() {
        return category;
    }

    public SaleOrderPrice getSaleOrderPrice() {
        return saleOrderPrice;
    }

    public IssueDuration getDuration() {
        return duration;
    }
}
