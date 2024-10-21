package coupon.coupon.domain;

public interface DiscountPolicy {

    void validatePolicy(int discountAmount, int minimumOrderAmount);
}
