package coupon.domain;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CouponName {
    private static final int MAX_LENGTH = 30;

    @Column(nullable = false, length = MAX_LENGTH)
    private String name;

    public CouponName(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        validateExist(name);
        validateLength(name);
    }

    private void validateExist(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException("쿠폰 이름은 공백일 수 없습니다.");
        }
    }

    private void validateLength(String name) {
        if (name.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("쿠폰 이름의 길이는 최대 " + MAX_LENGTH + "자 이하여야 합니다.");
        }
    }
}
