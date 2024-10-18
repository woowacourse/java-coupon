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
public class DiscountAmount {

    @Min(1000)
    @Max(10000)
    @NotNull
    private long discountAmount;

    public DiscountAmount(long discountAmount) {
        validate(discountAmount);
        this.discountAmount = discountAmount;
    }

    private void validate(long discountAmount) {
        if (discountAmount < 1000 || discountAmount > 10000) {
            throw new IllegalArgumentException("할인 금액은 1000원 이상 10000원 이하여야 합니다.");
        }
        if (discountAmount % 500 != 0) {
            throw new IllegalArgumentException("할인 금액은 500원 단위로만 입력 가능합니다.");
        }
    }
}
