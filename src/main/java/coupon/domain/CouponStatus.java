package coupon.domain;

public enum CouponStatus {
    PREPARING, ISSUABLE, EXPIRED, DISCARDED;

    public boolean isNotIssuable() {
        return this != ISSUABLE;
    }

    public boolean isNotUsable() {
        return this == PREPARING || this == DISCARDED;
    }
}
