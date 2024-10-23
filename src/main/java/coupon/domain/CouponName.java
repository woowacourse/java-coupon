package coupon.domain;

import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponName {

    private static final int MAX_NAME_LENGTH = 30;

    @Column(nullable = false, length = MAX_NAME_LENGTH)
    private String name;

    public CouponName(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        validateIsNullAndEmpty(name, "쿠폰 이름은 반드시 존재해야 합니다.");
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("쿠폰 이름은 최대 30자 이하여야 합니다.");
        }
    }

    private void validateIsNullAndEmpty(String value, String exceptionMessage) {
        if (Objects.isNull(value) || value.isEmpty()) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }
}
