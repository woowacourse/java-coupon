package coupon.domain;

import lombok.Getter;

@Getter
public class OrderPrice {

    private static final int MINIMUM_ORDER_PRICE = 5_000;
    private static final int MAXIMUM_ORDER_PRICE = 100_000;
    
    private final int price;

    public OrderPrice(int price) {
        validateOrderPrice(price);
        this.price = price;
    }

    private void validateOrderPrice(int price) {
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
