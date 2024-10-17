package coupon.coupon.domain;

public class Discount {

    private final int price;
    private final int percent;

    public Discount(int price, int minimumOrderAmount) {
        this.price = price;
        this.percent = price / minimumOrderAmount;
    }
}
