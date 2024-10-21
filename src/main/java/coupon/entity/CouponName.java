package coupon.entity;

import coupon.exception.CouponNameException;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class CouponName {

    private String name;

    public CouponName(String name) {
        this.name = name;
        validate();
    }

    private void validate() {
        if (name == null || name.isEmpty() || name.length() > 30) {
            throw new CouponNameException(name);
        }
    }
}
