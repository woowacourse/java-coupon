package coupon.domain.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter(value = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiscountAmount {

    private static final int MIN_DISCOUNT_AMOUNT = 1_000;
    private static final int MAX_DISCOUNT_AMOUNT = 10_000;
    private static final int DISCOUNT_AMOUNT_UNIT = 500;

    @Column(nullable = false)
    private int discountAmount;

    protected DiscountAmount(int discountAmount) {
        validateDiscountAmount(discountAmount);
        this.discountAmount = discountAmount;
    }

    private void validateDiscountAmount(int discountAmount) {
        if (discountAmount < MIN_DISCOUNT_AMOUNT || discountAmount > MAX_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException("할인 금액은 %d원 이상 %d원 이하여야 합니다."
                    .formatted(MIN_DISCOUNT_AMOUNT, MAX_DISCOUNT_AMOUNT));
        }

        if (discountAmount % DISCOUNT_AMOUNT_UNIT != 0) {
            throw new IllegalArgumentException("할인 금액은 %d원 단위만 가능합니다.".formatted(DISCOUNT_AMOUNT_UNIT));
        }
    }
}
