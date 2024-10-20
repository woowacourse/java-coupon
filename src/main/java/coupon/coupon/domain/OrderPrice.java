package coupon.coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderPrice {

    public static final int MIN_ORDER_PRICE = 5_000;
    public static final int MAX_ORDER_PRICE = 100_000;

    @Column(nullable = false, name = "order_price")
    private int value;

    public OrderPrice(int value) {
        validateMinOrderPrice(value);
        this.value = value;
    }

    private void validateMinOrderPrice(int value) {
        if (value < MIN_ORDER_PRICE || value > MAX_ORDER_PRICE) {
            throw new IllegalArgumentException(
                    String.format("최소 주문 금액은 %d원 이상, %d원 이하이어야 합니다.", MIN_ORDER_PRICE, MAX_ORDER_PRICE)
            );
        }
    }
}
