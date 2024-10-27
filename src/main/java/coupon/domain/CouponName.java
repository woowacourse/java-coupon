package coupon.domain;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public final class CouponName {

    private static final int MAX_LENGTH = 30;

    @Column(nullable = false)
    private String name;

    public CouponName(String name) {
        this.name = validate(name);
    }

    private String validate(String name) {
        requireExists(name);
        requireLengthUnderMax(name);
        return name;
    }

    private void requireExists(String name) {
        var message = "쿠폰 이름을 입력해야 합니다.";
        Objects.requireNonNull(name, message);
        if (name.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }

    private void requireLengthUnderMax(String name) {
        if (name.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(String.format("쿠폰 이름의 길이는 %d자 이하입니다.", MAX_LENGTH));
        }
    }
}
