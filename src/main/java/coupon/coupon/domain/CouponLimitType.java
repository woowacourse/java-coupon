package coupon.coupon.domain;

public enum CouponLimitType {
    NONE, ISSUE_COUNT, USE_COUNT;

    public boolean isNotIssueCountLimit() {
        return this != ISSUE_COUNT;
    }

    public boolean isNotUseCountLimit() {
        return this != USE_COUNT;
    }
}
