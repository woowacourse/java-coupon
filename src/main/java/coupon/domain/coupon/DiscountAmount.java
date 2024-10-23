package coupon.domain.coupon;

import jakarta.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiscountAmount {

    private int value;

    public DiscountAmount(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("최대 입력 가능한 금액을 초과했습니다.");
        }

        if (value < 1000 || value > 10000 || value % 500 != 0) {
            throw new IllegalArgumentException("할인 금액은 1000원 이상 10000원 이하의 500으로 나눠지는 금액만 가능합니다.");
        }
    }
}
