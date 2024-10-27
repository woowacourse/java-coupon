package coupon.domain.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MinOrderAmount {

    private static final int MIN_AMOUNT = 5_000;
    private static final int MAX_AMOUNT = 100_000;

    @Column(name = "min_order_amount", nullable = false)
    private int value;

    public MinOrderAmount(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        validateMin(value);
        validateMax(value);
    }

    private void validateMin(int value) {
        if (value < MIN_AMOUNT) {
            throw new IllegalArgumentException("최소 주문 금액은 " + MIN_AMOUNT + "원 이상이어야 합니다.");
        }
    }

    private void validateMax(int value) {
        if (value > MAX_AMOUNT) {
            throw new IllegalArgumentException("최소 주문 금액은 " + MAX_AMOUNT + "원 이하여야 합니다.");
        }
    }
}
