package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponName {

    private static final int MAX_NAME_LENGTH = 30;

    @Column(name = "name", nullable = false)
    private String name;

    public CouponName(String name) {
        validateCouponName(name);
        this.name = name;
    }

    private void validateCouponName(String name) {
        validatePresence(name);
        validateNameLength(name);
    }

    private void validatePresence(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("쿠폰 이름은 반드시 존재해야 합니다.");
        }
    }

    private void validateNameLength(String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("쿠폰 이름 길이는 최대 30자 이하여야 합니다.");
        }
    }
}
