package coupon.domain.coupon;

import java.math.BigDecimal;

import jakarta.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiscountAmount {

    private int value;

    public DiscountAmount(BigDecimal value) {
        validate(value);
        this.value = value.intValue();
    }

    private void validate(BigDecimal value) {
        if (value.compareTo(BigDecimal.valueOf(1000)) < 0 ||
                value.compareTo(BigDecimal.valueOf(10000)) > 0 ||
                value.remainder(BigDecimal.valueOf(500)).compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalArgumentException("할인 금액은 1000원 이상 10000원 이하의 500으로 나눠지는 금액만 가능합니다.");
        }
    }
}
