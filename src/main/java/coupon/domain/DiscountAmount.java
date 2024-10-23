package coupon.domain;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class DiscountAmount {

    public static final int MIN_DISCOUNT_AMOUNT = 1000;
    public static final int MAX_DISCOUNT_AMOUNT = 10000;
    public static final int DISCOUNT_UNIT = 500;

    private int discountAmount;

    public DiscountAmount(int discountAmount) {
        validateDiscountAmount(discountAmount);
        this.discountAmount = discountAmount;
    }

    private void validateDiscountAmount(int discountAmount) {
        if (discountAmount < MIN_DISCOUNT_AMOUNT
                || discountAmount > MAX_DISCOUNT_AMOUNT
                || discountAmount % DISCOUNT_UNIT != 0) {
            throw new IllegalArgumentException(
                    String.format("할인 금액은 %d원단위의 %d원이상, %d원이하로 해야합니다.",
                            DISCOUNT_UNIT, MIN_DISCOUNT_AMOUNT, MAX_DISCOUNT_AMOUNT)
            );
        }
    }
}
