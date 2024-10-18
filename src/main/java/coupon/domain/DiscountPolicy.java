package coupon.domain;

public interface DiscountPolicy {
    void validate(long discountAmount, long minimumOrderPrice);
}
