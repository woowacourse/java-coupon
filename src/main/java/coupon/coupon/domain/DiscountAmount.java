package coupon.coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.text.NumberFormat;
import lombok.Getter;

@Embeddable
@Getter
public class DiscountAmount {

    private static final int MIN_DISCOUNT_AMOUNT = 1000;
    private static final int MAX_DISCOUNT_AMOUNT = 10000;
    private static final int DISCOUNT_UNIT = 500;

    @Column(name = "discount_amount")
    private int value;

    public DiscountAmount() {
    }

    public DiscountAmount(int discountAmount) {
        validateAmount(discountAmount);
        validateUnit(discountAmount);
        this.value = discountAmount;
    }

    private void validateAmount(int value) {
        if (value < MIN_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException(
                    "할인 금액은 %s원 이상이어야 합니다.".formatted(NumberFormat.getInstance().format(MIN_DISCOUNT_AMOUNT)));
        }
        if (value > MAX_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException(
                    "할인 금액은 %s원 이하여야 합니다.".formatted(NumberFormat.getInstance().format(MAX_DISCOUNT_AMOUNT)));
        }
    }

    private void validateUnit(int value) {
        if (value % DISCOUNT_UNIT != 0) {
            throw new IllegalArgumentException(
                    "할인 금액은 %s원 단위로만 설정할 수 있습니다."
                            .formatted(NumberFormat.getInstance().format(DISCOUNT_UNIT)));
        }
    }
}
