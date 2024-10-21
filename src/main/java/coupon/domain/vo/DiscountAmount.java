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
public class DiscountAmount {

    private static final int UNIT = 500;
    private static final int MINIMUM_VALUE = 1_000;
    private static final int MAXIMUM_VALUE = 10_000;

    @Column(name = "discount_amount", nullable = false)
    private int value;

    public DiscountAmount(int value) {
        validateValue(value);
        this.value = value;
    }

    private void validateValue(int discountAmount) {
        if (discountAmount % UNIT != 0) {
            throw new GlobalCustomException(ErrorMessage.INVALID_COUPON_DISCOUNT_AMOUNT_UNIT);
        }

        if (discountAmount < MINIMUM_VALUE || discountAmount > MAXIMUM_VALUE) {
            throw new GlobalCustomException(ErrorMessage.INVALID_COUPON_DISCOUNT_AMOUNT_RANGE);
        }
    }

    public int calculateDiscountRate(int orderPrice) {
        return value * 100 / orderPrice;
    }
}
