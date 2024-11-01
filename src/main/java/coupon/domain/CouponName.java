package coupon.domain;

import java.util.Objects;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CouponName {

    private static final int MAX_LENGTH = 30;

    private final String value;

    public CouponName(final String value) {
        validate(value);
        this.value = value.strip();
    }

    private void validate(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("쿠폰 이름이 비어있습니다.");
        }
        if (value.strip().length() > MAX_LENGTH) {
            throw new IllegalArgumentException(String.format("쿠폰 이름이 최대 길이 %s를 초과합니다.", MAX_LENGTH));
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final CouponName name)) {
            return false;
        }
        return Objects.equals(getValue(), name.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}
