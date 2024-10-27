package coupon.exception;

import java.math.BigDecimal;

public class CouponDiscountAmountException extends CouponException {

    public CouponDiscountAmountException(BigDecimal discountAmount) {
        super("할인 금액은 1,000원 이상 10,000원 이하여야 하고, 500원 단위여야 합니다. 입력된 할인 금액: " + discountAmount);
    }
}
