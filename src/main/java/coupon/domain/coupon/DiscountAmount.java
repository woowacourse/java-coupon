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

    private void validate(final int value) {
        if (value < 1000 || value > 10000 || value % 500 != 0) {
            throw new IllegalArgumentException();
        }
    }
}
