package coupon.domain.coupon;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponName {

    private static final int MAXIMUM_LENGTH = 30;

    private String name;

    public CouponName(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (StringUtils.isBlank(name)) {
            String message = "이름은 비어있을 수 없습니다.";
            throw new IllegalArgumentException(message);
        }

        if (name.length() > MAXIMUM_LENGTH) {
            String message = "이름의 길이는 최대 30자 이하여야 한다. 입력 값 : " + name.length();
            throw new IllegalArgumentException(message);
        }
    }
}
