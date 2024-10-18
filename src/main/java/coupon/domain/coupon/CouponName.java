package coupon.domain.coupon;

import jakarta.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponName {

    private String value;

    public CouponName(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (value.isEmpty() || value.length() > 30) {
            throw new IllegalArgumentException();
        }
    }
}
