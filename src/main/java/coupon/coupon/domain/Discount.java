package coupon.coupon.domain;

import coupon.coupon.exception.CouponErrorMessage;
import coupon.coupon.exception.CouponException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Embeddable
@Getter
public class Discount {

    private static final int MIN_DISCOUNT_PRICE = 1000;
    private static final int MAX_DISCOUNT_PRICE = 10000;
    private static final int DISCOUNT_PRICE_UNIT = 500;
    private static final int MIN_DISCOUNT_PERCENT = 3;
    private static final int MAX_DISCOUNT_PERCENT = 20;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int percent;

    protected Discount() {
    }

    public Discount(int price, int minimumOrderAmount) {
        validatePrice(price);
        this.price = price;
        this.percent = calculatePercent(price, minimumOrderAmount);
        validatePercent(percent);
    }

    private void validatePrice(int price) {
        if (price < MIN_DISCOUNT_PRICE || price > MAX_DISCOUNT_PRICE) {
            throw new CouponException(CouponErrorMessage.INVALID_DISCOUNT_PRICE);
        }
        if(price % DISCOUNT_PRICE_UNIT != 0) {
            throw new CouponException(CouponErrorMessage.INVALID_DISCOUNT_PRICE);
        }
    }

    private int calculatePercent(int price, int minimumOrderAmount) {
        BigDecimal priceDecimal = BigDecimal.valueOf(price);
        BigDecimal minimumOrderAmountDecimal = BigDecimal.valueOf(minimumOrderAmount);

        BigDecimal percentDecimal = priceDecimal
                .divide(minimumOrderAmountDecimal, 2, RoundingMode.DOWN)
                .multiply(BigDecimal.valueOf(100));

        return percentDecimal.intValue();
    }

    private void validatePercent(int percent) {
        if (percent < MIN_DISCOUNT_PERCENT || percent > MAX_DISCOUNT_PERCENT) {
            throw new CouponException(CouponErrorMessage.INVALID_DISCOUNT_PERCENT);
        }
    }
}
