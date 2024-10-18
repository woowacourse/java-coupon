package coupon.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
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
}
