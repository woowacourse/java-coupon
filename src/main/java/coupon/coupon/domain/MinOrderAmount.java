package coupon.coupon.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MinOrderAmount {

    private static final int MIN_ORDER_AMOUNT = 0;

    private int minOrderAmount;

    public MinOrderAmount(int minOrderAmount) {
        validateOrderAmount(minOrderAmount);
        this.minOrderAmount = minOrderAmount;
    }

    private void validateOrderAmount(int orderAmount) {
        if (orderAmount < MIN_ORDER_AMOUNT) {
            throw new IllegalArgumentException("최소 주문 금액이 %d원 보다 작을 수 없습니다.".formatted(MIN_ORDER_AMOUNT));
        }
    }

    public int getValue() {
        return minOrderAmount;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MinOrderAmount that = (MinOrderAmount) o;
        return minOrderAmount == that.minOrderAmount;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(minOrderAmount);
    }
}
