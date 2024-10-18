package coupon.domain.coupon.discount;

@FunctionalInterface
public interface DiscountPolicy {

    boolean validate(int minOrderPrice, int discountPrice);
}
