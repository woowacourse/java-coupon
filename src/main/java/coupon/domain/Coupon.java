package coupon.domain;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CouponName name;

    @Embedded
    private SalePrice salePrice;

    @Embedded
    private SaleRatio saleRatio;

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
        this.salePrice = new SalePrice(salePrice);
        this.category = category;
        this.saleOrderPrice = new SaleOrderPrice(orderPrice);
        this.saleRatio = new SaleRatio(salePrice, orderPrice);
        this.duration = new IssueDuration(startTime, endTime);
    }

    protected Coupon() {
    }

    public Long getId() {
        return id;
    }
}
