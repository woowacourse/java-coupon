package coupon.coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class CouponName {

    private static final int MAX_COUPON_NAME_LENGTH = 30;

    @Column(name = "name")
    private String value;

    public CouponName() {
    }

    public CouponName(String value) {
        validateEmptyValue(value);
        validateCouponNameLength(value);
        this.value = value;
    }

    private void validateEmptyValue(String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException("쿠폰 이름이 비어있습니다.");
        }
    }

    private void validateCouponNameLength(String value) {
        if (value.length() > MAX_COUPON_NAME_LENGTH) {
            throw new IllegalArgumentException(
                    "쿠폰 이름의 길이가 %d자를 초과합니다. 쿠폰 이름 : ".formatted(MAX_COUPON_NAME_LENGTH)+ value);
        }
    }
}
