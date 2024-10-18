package coupon.domain.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CouponName {

    private static final int NAME_MAX_LENGTH = 30;

    @Column(name = "coupon_name", nullable = false)
    private String value;

    public CouponName(String value) {
        validateValue(value);
        this.value = value;
    }

    protected CouponName() {
        this.value = null;
    }

    private void validateValue(String rawValue) {
        if (rawValue == null) {
            throw new IllegalArgumentException("이름이 null입니다.");
        }

        if (rawValue.isBlank() || rawValue.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException("이름은 0자 이상 " + NAME_MAX_LENGTH + "자 미만이어야 합니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
