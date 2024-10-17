package coupon.coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponName {

    public static final int COUPON_NAME_LENGTH = 30;

    @Column(nullable = false, name = "name")
    private String value;

    public CouponName(String value) {
        validateNameIsBlank(value);
        validateNameLength(value);
        this.value = value;
    }

    private void validateNameIsBlank(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("쿠폰 이름은 반드시 필요 합니다.");
        }
    }

    private void validateNameLength(String value) {
        if (value.length() > COUPON_NAME_LENGTH) {
            throw new IllegalArgumentException("쿠폰 이름은 최대 30자 이하이어야 합니다.");
        }
    }
}
