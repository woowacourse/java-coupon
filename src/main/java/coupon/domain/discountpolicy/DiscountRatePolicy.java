package coupon.domain.discountpolicy;


import coupon.domain.DiscountPolicy;

public class DiscountRatePolicy implements DiscountPolicy {

    private static final int MINIMUM_ORDER_PRICE_REQUIRED = 1;
    private static final int MINIMUM_DISCOUNT_RATE = 3;
    private static final int MAXIMUM_DISCOUNT_RATE = 20;

    private final int discountRate;

    public DiscountRatePolicy(int discountMoney, int minimumOrderPrice) {
        validateMinimumOrderRate(minimumOrderPrice);
        int discountRate = (discountMoney * 100) / minimumOrderPrice;
        validateDiscountRateRange(discountRate);
        this.discountRate = discountRate;
    }

    private void validateMinimumOrderRate(int minimumOrderPrice) {
        if (minimumOrderPrice < MINIMUM_ORDER_PRICE_REQUIRED) {
            throw new IllegalArgumentException(
                    "최소 주문 금액은 [%d] 보다 작은 정수가 될 수 없습니다.".formatted(MINIMUM_ORDER_PRICE_REQUIRED));
        }
    }

    private void validateDiscountRateRange(int discountRate) {
        if (discountRate < MINIMUM_DISCOUNT_RATE) {
            throw new IllegalArgumentException("할인율은 [%d] 이상이어야 합니다.".formatted(MINIMUM_DISCOUNT_RATE));
        }

        if (discountRate > MAXIMUM_DISCOUNT_RATE) {
            throw new IllegalArgumentException("할인율은 [%d] 이하어야 합니다.".formatted(MAXIMUM_DISCOUNT_RATE));
        }
    }

    @Override
    public int apply(int money) {
        return money * (discountRate / 100);
    }

    public int getDiscountRate() {
        return discountRate;
    }
}
