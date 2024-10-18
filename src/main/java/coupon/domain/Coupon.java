package coupon.domain;

import java.time.LocalDate;
import java.util.Objects;

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

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Coupon {
    private static final int MIN_PERCENT = 3;
    private static final int MAX_PERCENT = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "coupon_name"))
    private CouponName couponName;

    @Embedded
    @AttributeOverride(name = "price", column = @Column(name = "discount_price"))
    private DiscountPrice discountPrice;

    @Embedded
    @AttributeOverride(name = "price", column = @Column(name = "min_order_price"))
    private MinimumOrderPrice minOrderPrice;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "startDate", column = @Column(name = "start_date")),
            @AttributeOverride(name = "endDate", column = @Column(name = "end_date"))
    })
    private Duration duration;

    public Coupon(String couponName, int discountPrice, int minOrderPrice,
            Category category, LocalDate startDate, LocalDate endDate
    ) {
        this.couponName = new CouponName(couponName);
        this.discountPrice = new DiscountPrice(discountPrice);
        this.minOrderPrice = new MinimumOrderPrice(minOrderPrice);
        validateDiscountPercent(this.discountPrice, this.minOrderPrice);
        this.category = category;
        duration = new Duration(startDate, endDate);
    }

    private void validateDiscountPercent(DiscountPrice discountPrice, MinimumOrderPrice minimumOrderPrice) {
        int discountPercent = discountPrice.getPrice() * 100 / minimumOrderPrice.getPrice();
        if (discountPercent < MIN_PERCENT || discountPercent > MAX_PERCENT) {
            throw new IllegalArgumentException("할인율은 %d %% 이상, %d %% 이하 여야 합니다.".formatted(MIN_PERCENT, MAX_PERCENT));
        }
    }
}
