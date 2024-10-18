package coupon.domain.coupon;

import coupon.exception.CouponException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponName {

    @Column(nullable = false)
    private String name;

    public CouponName(String name) {
        validateLength(name);
        this.name = name;
    }

    private void validateLength(String name) {
        if (name == null || name.isBlank() || name.length() > 30) {
            throw new CouponException("쿠폰 이름은 1자 이상 30자 이하여야 합니다.");
        }
    }
}
