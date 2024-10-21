package coupon.coupon.domain;


import lombok.Getter;

@Getter
public class DiscountRate {

    private static final Long MINIMUM_DISCOUNT_RATE = 3L;
    private static final Long MAXIMUM_DISCOUNT_RATE = 20L;

    private final Long discountRate;

    public DiscountRate(DiscountMoney discountMoney, OrderPrice orderPrice) {
        Long discountRate = (discountMoney.getDiscountAmount() * 100) / orderPrice.getPrice();
        validateDiscountRateRange(discountRate);
        this.discountRate = discountRate;
    }

    public DiscountRate(Long discountMoney, Long orderPrice) {
        this(new DiscountMoney(discountMoney), new OrderPrice(orderPrice));
    }

    private void validateDiscountRateRange(Long discountRate) {
        if (discountRate < MINIMUM_DISCOUNT_RATE) {
            throw new IllegalArgumentException("할인율은 [%d] 이상이어야 합니다.".formatted(MINIMUM_DISCOUNT_RATE));
        }

        if (discountRate > MAXIMUM_DISCOUNT_RATE) {
            throw new IllegalArgumentException("할인율은 [%d] 이하어야 합니다.".formatted(MAXIMUM_DISCOUNT_RATE));
        }
    }
}
