package coupon.domain;


import lombok.Getter;

@Getter
public class DiscountRate {

    private static final int MINIMUM_DISCOUNT_RATE = 3;
    private static final int MAXIMUM_DISCOUNT_RATE = 20;

    private final int discountRate;

    public DiscountRate(DiscountMoney discountMoney, OrderPrice orderPrice) {
        int discountRate = (discountMoney.getDiscountAmount() * 100) / orderPrice.getPrice();
        validateDiscountRateRange(discountRate);
        this.discountRate = discountRate;
    }

    public DiscountRate(int discountMoney, int orderPrice) {
        this(new DiscountMoney(discountMoney), new OrderPrice(orderPrice));
    }

    private void validateDiscountRateRange(int discountRate) {
        if (discountRate < MINIMUM_DISCOUNT_RATE) {
            throw new IllegalArgumentException("할인율은 [%d] 이상이어야 합니다.".formatted(MINIMUM_DISCOUNT_RATE));
        }

        if (discountRate > MAXIMUM_DISCOUNT_RATE) {
            throw new IllegalArgumentException("할인율은 [%d] 이하어야 합니다.".formatted(MAXIMUM_DISCOUNT_RATE));
        }
    }
}
