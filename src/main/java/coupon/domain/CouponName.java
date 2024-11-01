package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponName {

    private static final int MAX_LENGTH = 30;

    @Column(nullable = false, length = MAX_LENGTH)
    private String name;

    public CouponName(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        if (name.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("쿠폰 이름은 " + MAX_LENGTH + "자 이하여야 합니다.");
        }
    }
}
