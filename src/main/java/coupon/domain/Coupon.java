package coupon.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "Coupon")
public class Coupon {

    private static final int MIN_DISCOUNT_PERCENT = 3;
    private static final int MAX_DISCOUNT_PERCENT = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    private CouponName couponName;

    @Embedded
    private DiscountPrice discountPrice;

    @Embedded
    private MinimumOrderPrice minOrderPrice;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Embedded
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
        if (discountPercent < MIN_DISCOUNT_PERCENT || discountPercent > MAX_DISCOUNT_PERCENT) {
            throw new IllegalArgumentException("할인율은 %d %% 이상, %d %% 이하 여야 합니다.".formatted(MIN_DISCOUNT_PERCENT,
                    MAX_DISCOUNT_PERCENT));
        }
    }
}
