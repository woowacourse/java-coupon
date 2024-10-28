package coupon.domain.coupon;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CouponName {

    private static final int NAME_LENGTH_MAX = 30;

    private String name;

    public CouponName(String name) {
        validateLength(name);
        this.name = name;
    }

    private void validateLength(String name) {
        if (name.length() > NAME_LENGTH_MAX) {
            throw new IllegalArgumentException("쿠폰 이름의 길이는 30 초과일 수 없습니다: %s".formatted(name));
        }
    }
}
