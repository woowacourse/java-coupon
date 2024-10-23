package coupon.domain;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class MinimumOrderPrice {

    public static final int MIN_ORDER_PRICE = 5000;
    public static final int MAX_ORDER_PRICE = 100_000;
    private int minimumOrderPrice;

    public MinimumOrderPrice(int minimumOrderPrice) {
        validateMinimumOrderPrice(minimumOrderPrice);
        this.minimumOrderPrice = minimumOrderPrice;
    }

    private void validateMinimumOrderPrice(int minimumOrderPrice) {
        if (minimumOrderPrice < MIN_ORDER_PRICE || minimumOrderPrice > MAX_ORDER_PRICE) {
            throw new IllegalArgumentException(
                    String.format("최소 주문 금액은 %d이상 %d이하여야합니다.", MIN_ORDER_PRICE, MAX_ORDER_PRICE)
            );
        }
    }
}
