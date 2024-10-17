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
public class CouponMinOrderAmount {

    private static final Money MIN_ORDER_AMOUNT_SIZE = Money.wons(5_000);
    private static final Money MAX_ORDER_AMOUNT_SIZE = Money.wons(100_000);

    @Column(name = "min_order_amount", nullable = false)
    @Convert(converter = MoneyConverter.class)
    private Money value;

    public CouponMinOrderAmount(Money minOrderAmount) {
        validateOrderAmountSize(minOrderAmount);

        this.value = minOrderAmount;
    }

    private void validateOrderAmountSize(Money minOrderAmount) {
        if (minOrderAmount.isLessThan(MIN_ORDER_AMOUNT_SIZE) || MAX_ORDER_AMOUNT_SIZE.isLessThan(minOrderAmount)) {
            String message = "주문 최소 금액은 %,d원 이상 %,d원 이하만 가능합니다."
                    .formatted(MIN_ORDER_AMOUNT_SIZE.longValue(), MAX_ORDER_AMOUNT_SIZE.longValue());

            throw new IllegalArgumentException(message);
        }
    }
}
