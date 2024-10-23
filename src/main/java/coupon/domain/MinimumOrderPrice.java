package coupon.domain;

import jakarta.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class MinimumOrderPrice {

    private static final int MIN_PRICE = 5_000;
    private static final int MAX_PRICE = 100_000;
    private int price;

    public MinimumOrderPrice(int price) {
        if (price < MIN_PRICE || price > MAX_PRICE) {
            throw new IllegalArgumentException("최소 주문 금액은 %d 원 이상, %d 원 이하여야 합니다.".formatted(MIN_PRICE, MAX_PRICE));
        }
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
