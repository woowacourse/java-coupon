package coupon.coupon.domain;

import lombok.Getter;

@Getter
public class OrderPrice {

    private static final Long MINIMUM_ORDER_PRICE = 5_000L;
    private static final Long MAXIMUM_ORDER_PRICE = 100_000L;

    private final Long price;

    public OrderPrice(Long price) {
        validateOrderPrice(price);
        this.price = price;
    }

    private void validateOrderPrice(Long price) {
        if (price < MINIMUM_ORDER_PRICE) {
            throw new IllegalArgumentException(
                    "최소 주문 금액은 [%d] 보다 작을 수 없습니다.".formatted(MINIMUM_ORDER_PRICE));
        }

        if (price > MAXIMUM_ORDER_PRICE) {
            throw new IllegalArgumentException(
                    "최소 주문 금액은 [%d] 보다 클 수 없습니다.".formatted(MAXIMUM_ORDER_PRICE));
        }
    }
}
