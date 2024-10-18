package coupon.domain;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponName {

    private static final int MAX_LENGTH = 30;

    @Column(name = "NAME", nullable = false)
    private String name;

    public CouponName(final String name) {
        validate(name);
        this.name = name;
    }

    private void validate(final String name) {
        validateNull(name);
        validateLength(name);
        validateBlank(name);
    }

    private void validateNull(final String name) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("쿠폰 이름에 NULL은 허용하지 않습니다.");
        }
    }

    private void validateLength(final String name) {
        if (name.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("쿠폰 이름은 %s자 이하여야 합니다.".formatted(MAX_LENGTH));
        }
    }

    private void validateBlank(final String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("쿠폰 이름에 빈값은 허용하지 않습니다.");
        }
    }
}
