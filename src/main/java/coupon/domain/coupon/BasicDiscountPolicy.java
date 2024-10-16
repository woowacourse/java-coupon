package coupon.domain.coupon;

import coupon.domain.coupon.exception.InvalidDiscountMoneyException;
import coupon.domain.coupon.exception.InvalidDiscountRateException;
import coupon.domain.coupon.exception.InvalidMinOrderMoneyException;

public abstract class BasicDiscountPolicy implements DiscountPolicy {

    public BasicDiscountPolicy(int discountMoney, int minOrderMoney) {
        validateDiscountMoney(discountMoney);
        validateMinOrderMoney(minOrderMoney);
        validateDiscountRate(discountMoney, minOrderMoney);
    }

    private void validateDiscountMoney(int discountMoney) {
        if (discountMoney < 1000 || discountMoney > 10000) {
            throw new InvalidDiscountMoneyException("할인 금액은 1000원 이상 10000원 이하여야 합니다.");
        }
        if (discountMoney % 500 != 0) {
            throw new InvalidDiscountMoneyException("할인 금액은 500원 단위로 설정할 수 있습니다.");
        }
    }

    private void validateDiscountRate(int discountMoney, int minOrderMoney) {
        int discountRate = discountMoney * 100 / minOrderMoney;
        if (discountRate < 3 || discountRate > 20) {
            throw new InvalidDiscountRateException("쿠폰의 할인율은 3% 이상 20% 이하여야 합니다.");
        }
    }

    private void validateMinOrderMoney(int minOrderMoney) {
        if (minOrderMoney < 5000 || minOrderMoney > 100000) {
            throw new InvalidMinOrderMoneyException("최소 주문 금액은 5,000원 이상 100,000원 이하여야 합니다.");
        }
    }
}
