package coupon;

record CouponName(String value) {

    private static final int COUPON_NAME_MAX_LENGTH = 30;

    CouponName {
        if (value.isBlank()) {
            throw new IllegalArgumentException("쿠폰 이름은 반드시 존재해야 합니다.");
        }

        if (value.length() > COUPON_NAME_MAX_LENGTH) {
            throw new IllegalArgumentException("쿠폰 이름 길이는 최대 " + COUPON_NAME_MAX_LENGTH + "자 이하입니다.");
        }
    }
}
