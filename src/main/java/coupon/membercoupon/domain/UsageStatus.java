package coupon.membercoupon.domain;

public enum UsageStatus {

    USE, NON_USE;

    public boolean isUse() {
        return this == USE;
    }
}
