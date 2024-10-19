package coupon.domain;

import coupon.exception.CouponException;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
public class CouponName {

    private String name;

    public CouponName(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new CouponException("쿠폰 이름은 반드시 존재해야 합니다.");
        }

        if (name.length() > 30) {
            throw new CouponException("쿠폰 이름은 최대 30자 이하여야 합니다.");
        }
    }
}
