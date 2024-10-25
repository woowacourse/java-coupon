package coupon.domain.coupon;

import java.util.Objects;

import lombok.Getter;

@Getter
public class CouponName {

    private static final int MAXIMUM_COUPON_NAME_LENGTH_LIMIT = 30;

    private final String value;

    public CouponName(final String value) {
        validateValueIsNullOrBlank(value);
        validateValueLength(value);
        this.value = value;
    }

    private void validateValueIsNullOrBlank(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("쿠폰 이름은 null 혹은 빈 값을 입력할 수 없습니다.");
        }
    }

    private void validateValueLength(final String value) {
        if (value.length() > MAXIMUM_COUPON_NAME_LENGTH_LIMIT) {
            throw new IllegalArgumentException("쿠폰 이름의 길이는 최대 " + MAXIMUM_COUPON_NAME_LENGTH_LIMIT + "자 이하여야 합니다. - " + value.length());
        }
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof final CouponName that)) {
            return false;
        }
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
