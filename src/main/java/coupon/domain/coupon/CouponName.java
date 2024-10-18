package coupon.domain.coupon;

import coupon.exception.CouponException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponName {

    private static final int COUPON_NAME_MAX_LENGTH = 30;

    @Column(name = "name", nullable = false)
    private String value;


    public CouponName(String name) {
        validate(name);
        this.value = name;
    }

    private void validate(String name) {
        if (Strings.isBlank(name) || name.length() > COUPON_NAME_MAX_LENGTH) {
            throw new CouponException("쿠폰 이름은 30글자 이하로 반드시 존재 해야합니다.");
        }
    }
}
