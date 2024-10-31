package coupon.coupon.domain;

import lombok.Getter;

@Getter
public class CouponName {

    static final int MAX_NAME_LENGTH = 30;

    private final String name;

    public CouponName(String name) {
        validateName(name);
        this.name = name;
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank() || name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(String.format(ExceptionMessage.NAME_LENGTH_EXCEPTION.getMessage(),
                    MAX_NAME_LENGTH));
        }
    }
}
