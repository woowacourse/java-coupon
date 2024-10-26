package coupon.domain.coupon;

import java.math.BigDecimal;

import jakarta.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class OrderAmount {

    private int value;

    public OrderAmount(BigDecimal value) {
        validate(value);
        this.value = value.intValue();
    }

    private void validate(BigDecimal value) {
        if (value.compareTo(BigDecimal.valueOf(5000)) < 0 || value.compareTo(BigDecimal.valueOf(100000)) > 0) {
            throw new IllegalArgumentException("주문금액은 5000원 이상 100000 이하만 가능합니다.");
        }
    }
}
