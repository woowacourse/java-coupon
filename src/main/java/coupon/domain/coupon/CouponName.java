package coupon.domain.coupon;

import jakarta.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponName {

    private String value;

    public CouponName(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (value == null || value.isBlank() || value.length() > 30) {
            throw new IllegalArgumentException("1 이상 30 이하의 쿠폰 이름을 입력해주세요.");
        }
    }
}
