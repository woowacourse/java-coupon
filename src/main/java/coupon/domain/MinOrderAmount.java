package coupon.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MinOrderAmount {

    @Min(5000)
    @Max(100000)
    @NotNull
    private long minOrderAmount;

    public MinOrderAmount(long minOrderAmount) {
        validate(minOrderAmount);
        this.minOrderAmount = minOrderAmount;
    }

    private void validate(long minOderAmount) {
        if (minOderAmount < 5000 || minOderAmount > 100000) {
            throw new IllegalArgumentException("최소 주문 금액은 5000원 이상 100000원 이하여야 합니다.");
        }
    }
}
