package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class DiscountPrice {

    private static final int MIN_PRICE = 1_000;
    private static final int MAX_PRICE = 10_000;
    private static final int UNIT_PRICE = 500;

    @Column(name = "discount_price")
    private int price;

    public DiscountPrice(int price) {
        if (price < MIN_PRICE || price > MAX_PRICE) {
            throw new IllegalArgumentException("할인 금액은 %d 원 이상, %d 원 이하여야 합니다.".formatted(MIN_PRICE, MAX_PRICE));
        }
        if (price % UNIT_PRICE != 0) {
            throw new IllegalArgumentException("할인 금액은 %d 원 단위여야 합니다.".formatted(UNIT_PRICE));
        }

        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
