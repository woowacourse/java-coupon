package coupon.domain;

public class Coupon {

    private final DiscountAmount discountAmount;
    private final MinimumOrderPrice minimumOrderPrice;

    public Coupon(int discountAmount, int minimumOrderPrice) {
        this.discountAmount = new DiscountAmount(discountAmount);
        this.minimumOrderPrice = new MinimumOrderPrice(minimumOrderPrice);
    }
}
