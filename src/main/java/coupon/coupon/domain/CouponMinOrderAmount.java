package coupon.coupon.domain;

import lombok.Getter;

@Getter
public class CouponMinOrderAmount {

    public static final int MIN_ORDER_AMOUNT = 5000;
    public static final int MAX_ORDER_AMOUNT = 100_000;

    private int amount;

    public CouponMinOrderAmount(int minOrderAmount) {
        validateMinOrderAmountRange(minOrderAmount);
        this.amount = minOrderAmount;
    }

    private void validateMinOrderAmountRange(int minOrderAmount) {
        if (minOrderAmount < MIN_ORDER_AMOUNT || minOrderAmount > MAX_ORDER_AMOUNT) {
            throw new IllegalArgumentException("쿠폰의 최소 주문 금액은 %d원 이상, %d원 이하여야 해요."
                    .formatted(MIN_ORDER_AMOUNT, MAX_ORDER_AMOUNT));
        }
    }
}
