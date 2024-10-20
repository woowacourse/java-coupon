package coupon.domain.coupon;

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
public class Coupon {

    private static final double MIN_DISCOUNT_RATIO = 3.0;
    private static final double MAX_DISCOUNT_RATIO = 20.0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CouponName name;

    @Embedded
    private DiscountPrice discountPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Embedded
    private SaleOrderPrice saleOrderPrice;

    @Embedded
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

    public Long getId() {
        return id;
    }

    public DiscountRatio getDiscountRatio() {
        return new DiscountRatio(100 * discountPrice.getPrice() / saleOrderPrice.getPrice());
    }
}
