package coupon.domain;

import java.util.Objects;

public class OrderAmount {

    private static final int MIN_ORDER_AMOUNT = 5_000;
    private static final int MAX_ORDER_AMOUNT = 100_000;
    private final int orderAmount;

    public OrderAmount(int orderAmount) {
        validateOrderAmount(orderAmount);
        this.orderAmount = orderAmount;
    }

    private void validateOrderAmount(int orderAmount) {
        if (orderAmount < MIN_ORDER_AMOUNT || MAX_ORDER_AMOUNT < orderAmount) {
            throw new IllegalArgumentException("주문 금액은 %d원 이상 ~ %d원 이하여야 합니다.".formatted(MIN_ORDER_AMOUNT, MAX_ORDER_AMOUNT));
        }
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderAmount that = (OrderAmount) o;
        return orderAmount == that.orderAmount;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderAmount);
    }
}
