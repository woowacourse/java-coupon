package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MinOrderAmount {

    @Min(5000)
    @Max(100000)
    @Column(nullable = false, name = "min_order_amount")
    private long amount;

    public MinOrderAmount(long amount) {
        validate(amount);
        this.amount = amount;
    }

    private void validate(long amount) {
        if (amount < 5000 || amount > 100000) {
            throw new IllegalArgumentException("최소 주문 금액은 5000원 이상 100000원 이하여야 합니다.");
        }
    }
}
