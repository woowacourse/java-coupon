package coupon.domain.coupon;

import jakarta.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class OrderAmount {

    private int value;

    public OrderAmount(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value < 5000 || value > 100000) {
            throw new IllegalArgumentException();
        }
    }
}
