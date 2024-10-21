package coupon.coupon.domain;

import lombok.Getter;

@Getter
public class CouponName {

    private static final int MAX_LENGTH = 30;
    private final String name;

    public CouponName(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("쿠폰 이름은 필수입니다.");
        }
        if (name.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("쿠폰 이름 길이는 30자 이하여야 한다.");
        }
    }
}
