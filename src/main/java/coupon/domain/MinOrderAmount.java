package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MinOrderAmount {

    private static final int MIN_AMOUNT = 5000;
    private static final int MAX_AMOUNT = 100000;

    @Column(nullable = false, name = "min_order_amount")
    private long amount;

    public MinOrderAmount(long amount) {
        validate(amount);
        this.amount = amount;
    }

    private void validate(long amount) {
        if (amount < MIN_AMOUNT || amount > MAX_AMOUNT) {
            throw new IllegalArgumentException("최소 주문 금액은 5000원 이상 100000원 이하여야 합니다.");
        }
    }
}
