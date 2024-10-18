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
public class DiscountAmount {

    @Min(1000)
    @Max(10000)
    @Column(nullable = false, name = "discount_amount")
    private long amount;

    public DiscountAmount(long amount) {
        validate(amount);
        this.amount = amount;
    }

    private void validate(long amount) {
        if (amount < 1000 || amount > 10000) {
            throw new IllegalArgumentException("할인 금액은 1000원 이상 10000원 이하여야 합니다.");
        }
        if (amount % 500 != 0) {
            throw new IllegalArgumentException("할인 금액은 500원 단위로만 입력 가능합니다.");
        }
    }
}
