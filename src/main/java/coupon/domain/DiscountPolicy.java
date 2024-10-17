package coupon.domain;

public interface DiscountPolicy {

    void validatePolicy(long fixedDiscountAmount, long minimumOrderPrice)
            throws DiscountPolicyViolationException;
}
