package coupon.coupon.service;

import org.springframework.stereotype.Component;

@Component
public class CouponMinOrderAmountValidator {

    public static final int MIN_ORDER_AMOUNT = 5000;
    public static final int MAX_ORDER_AMOUNT = 100_000;

    public void validate(int couponDiscountAmount) {
        if (couponDiscountAmount < MIN_ORDER_AMOUNT || couponDiscountAmount > MAX_ORDER_AMOUNT) {
            throw new IllegalArgumentException("쿠폰의 최소 주문 금액은 %d원 이상, %d원 이하여야 해요."
                    .formatted(MIN_ORDER_AMOUNT, MAX_ORDER_AMOUNT));
        }
    }
}
