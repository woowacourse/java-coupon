package coupon.domain.coupon.discount;

public class PercentDiscountPolicy implements DiscountPolicy {

    private final int minDiscountPercent;
    private final int maxDiscountPercent;

    public PercentDiscountPolicy(int minDiscountPercent, int maxDiscountPercent) {
        this.minDiscountPercent = minDiscountPercent;
        this.maxDiscountPercent = maxDiscountPercent;
    }

    @Override
    public boolean validate(int minOrderPrice, int discountPrice) {
        int discountPercent = discountPrice / minOrderPrice;

        return (discountPercent < minDiscountPercent) || (discountPercent > maxDiscountPercent);
    }
}
