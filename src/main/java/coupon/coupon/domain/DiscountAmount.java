package coupon.coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiscountAmount {

    public static final int MIN_DISCOUNT_AMOUNT = 1_000;
    public static final int MAX_DISCOUNT_AMOUNT = 10_000;
    public static final int DISCOUNT_AMOUNT_UNIT = 500;

    @Column(nullable = false, name = "discount_amount")
    private int amount;

    public DiscountAmount(int value) {
        validateDiscountAmount(value);
        validateDiscountAmountUnit(value);
        this.amount = value;
    }

    private void validateDiscountAmount(int value) {
        if (value < MIN_DISCOUNT_AMOUNT || value > MAX_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException(
                    String.format("할인 금액은 %d원 이상, %d원 이하이어야 합니다.", MIN_DISCOUNT_AMOUNT, MAX_DISCOUNT_AMOUNT));
        }
    }

    private void validateDiscountAmountUnit(int value) {
        if (value % DISCOUNT_AMOUNT_UNIT != 0) {
            throw new IllegalArgumentException(String.format("할인 금액은 %d원 단위로 설정할 수 있습니다.", DISCOUNT_AMOUNT_UNIT));
        }
    }
}
