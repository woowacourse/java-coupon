package coupon.coupon.domain;

import coupon.common.domain.Money;
import coupon.common.infra.converter.MoneyConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponDiscountAmount {

    private static final Money MIN_DISCOUNT_AMOUNT_SIZE = Money.wons(1_000);
    private static final Money MAX_DISCOUNT_AMOUNT_SIZE = Money.wons(10_000);
    private static final Money DISCOUNT_AMOUNT_UNIT = Money.wons(500);
    private static final int MIN_DISCOUNT_RATE = 3;
    private static final int MAX_DISCOUNT_RATE = 20;

    @Column(name = "discount_amount", nullable = false)
    @Convert(converter = MoneyConverter.class)
    private Money value;

    public CouponDiscountAmount(Money discountAmount, CouponMinOrderAmount minOrderAmount) {
        validate(discountAmount, minOrderAmount);

        this.value = discountAmount;
    }

    public void validate(Money discountAmount, CouponMinOrderAmount minOrderAmount) {
        validateDiscountAmountSize(discountAmount);
        validateDiscountAmountUnit(discountAmount);
        validateDiscountRate(discountAmount, minOrderAmount);
    }

    public void validateDiscountAmountSize(Money discountAmount) {
        if (discountAmount.isLessThan(MIN_DISCOUNT_AMOUNT_SIZE) || MAX_DISCOUNT_AMOUNT_SIZE.isLessThan(discountAmount)) {
            String message = "할인 금액은 %,d원 이상 %,d원 이하만 가능합니다."
                    .formatted(MIN_DISCOUNT_AMOUNT_SIZE.longValue(), MAX_DISCOUNT_AMOUNT_SIZE.longValue());

            throw new IllegalArgumentException(message);
        }
    }

    private void validateDiscountAmountUnit(Money discountAmount) {
        if (!discountAmount.isMultipleOf(DISCOUNT_AMOUNT_UNIT)) {
            String message = "할인 금액은 %,d원 단위로만 가능합니다.".formatted(DISCOUNT_AMOUNT_UNIT.longValue());

            throw new IllegalArgumentException(message);
        }
    }

    private void validateDiscountRate(Money discountAmount, CouponMinOrderAmount minOrderAmount) {
        int discountRate = calculateDiscountRate(discountAmount, minOrderAmount);
        if (discountRate < MIN_DISCOUNT_RATE || MAX_DISCOUNT_RATE < discountRate) {
            String message = "할인율은 %d%% 이상 %d%% 이하만 가능합니다.".formatted(MIN_DISCOUNT_RATE, MAX_DISCOUNT_RATE);

            throw new IllegalArgumentException(message);
        }
    }

    private int calculateDiscountRate(Money discountAmount, CouponMinOrderAmount minOrderAmount) {
        return discountAmount.ofRatio(minOrderAmount.getValue());
    }
}
