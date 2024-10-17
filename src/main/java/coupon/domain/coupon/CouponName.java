package coupon.domain.coupon;

import lombok.Getter;

@Getter
public class CouponName {

    private static final int MAX_LENGTH = 30;

    private final String name;

    public CouponName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("쿠폰 이름은 필수입니다.");
        }
        if (name.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("쿠폰 이름은 " + MAX_LENGTH + "자를 넘을 수 없습니다.");
        }
        this.name = name;
    }
}
