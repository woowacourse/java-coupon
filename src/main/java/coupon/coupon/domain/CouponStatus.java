package coupon.coupon.domain;

public enum CouponStatus {
    PREPARING, ISSUABLE, EXPIRED, DISCARDED;

    public boolean isNotIssuable() {
        return this != ISSUABLE;
    }

    /**
     * 폐기된 쿠폰은 사용할 수 없다.
     */
    public boolean isNotUsable() {
        return this == PREPARING || this == DISCARDED;
    }
}
