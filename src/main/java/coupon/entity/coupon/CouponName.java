package coupon.entity.coupon;

import coupon.exception.coupon.CouponNameException;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class CouponName {

    private static final int MAX_NAME_LENGTH = 30;
    private String name;

    public CouponName(String name) {
        this.name = name;
        validate();
    }

    private void validate() {
        if (name == null || name.isEmpty() || name.length() > MAX_NAME_LENGTH) {
            throw new CouponNameException(name);
        }
    }
}
