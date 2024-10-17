package coupon.domain.coupon;

public interface DiscountPolicy {

    void validatePolicy(long fixedDiscountAmount, long minimumOrderPrice)
            throws DiscountPolicyViolationException;
}
