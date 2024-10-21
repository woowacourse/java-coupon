package coupon.domain.vo;

import coupon.exception.ErrorMessage;
import coupon.exception.GlobalCustomException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class MinimumOrderPrice {

    private static final int MINIMUM_VALUE = 5_000;
    private static final int MAXIMUM_VALUE = 100_000;

    @Column(name = "minimum_order_price", nullable = false)
    private int value;

    public MinimumOrderPrice(int value) {
        validateValue(value);
        this.value = value;
    }

    private void validateValue(int minimumOrderPrice) {
        if (minimumOrderPrice < MINIMUM_VALUE || minimumOrderPrice > MAXIMUM_VALUE) {
            throw new GlobalCustomException(ErrorMessage.INVALID_MINIMUM_ORDER_PRICE_RANGE);
        }
    }
}
