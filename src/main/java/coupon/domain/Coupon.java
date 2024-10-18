package coupon.domain;

public class Coupon {

    private final Long id;
    private final DiscountAmount discountAmount;
    private final MinimumOrderPrice minimumOrderPrice;

    public Coupon(int discountAmount, int minimumOrderPrice) {
        this(null, discountAmount, minimumOrderPrice);
    }

    public Coupon(Long id, int discountAmount, int minimumOrderPrice) {
        this.id = id;
        this.discountAmount = new DiscountAmount(discountAmount);
        this.minimumOrderPrice = new MinimumOrderPrice(minimumOrderPrice);
    }

    public Long getId() {
        return id;
    }

    public int getDiscountAmount() {
        return discountAmount.getAmount();
    }

    public int getMinimumOrderPrice() {
        return minimumOrderPrice.getAmount();
    }
}
