package coupon.domain.coupon;

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
            throw new IllegalArgumentException("이름은 비어있을 수 없습니다.");
        }
        if (name.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("이름은 최대 " + MAX_LENGTH + "자 입니다.");
        }
    }
}
