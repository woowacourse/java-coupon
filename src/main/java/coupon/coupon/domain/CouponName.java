package coupon.coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponName {

    private static final int MAX_NAME_LENGTH = 30;

    @Column(name = "name", nullable = false)
    private String name;

    public CouponName(String name) {
        validateNotEmpty(name);
        validateNameLength(name);
        this.name = name;
    }

    private void validateNotEmpty(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("쿠폰 이름은 비어있을 수 없어요.");
        }
    }

    private void validateNameLength(String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(String.format("쿠폰 이름은 %d자 이하여야 해요.", MAX_NAME_LENGTH));
        }
    }
}
