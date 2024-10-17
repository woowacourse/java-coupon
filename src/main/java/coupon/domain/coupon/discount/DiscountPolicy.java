package coupon.domain.coupon.discount;

public interface DiscountPolicy {

    boolean validate(int minOrderPrice, int discountPrice);
}
