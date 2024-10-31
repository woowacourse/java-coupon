package coupon.domain;

import java.io.Serializable;

import coupon.exception.InvalidMinimumOrderAmount;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MinimumOrderAmount implements Serializable {

    private final long value;

    public MinimumOrderAmount(final long value) {
        validateOrderAmount(value);
        this.value = value;
    }

    private void validateOrderAmount(final long value) {
        if (value < 0) {
            throw new InvalidMinimumOrderAmount("최소 주문 금액이 올바르지 않습니다.");
        }
    }
}
