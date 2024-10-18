package coupon.domain.coupon;

public interface DiscountPolicy {
    void validate(long discountAmount, long minimumOrderPrice);
}
