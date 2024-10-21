package coupon.coupon.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class Discount {

    private int price;
    private int percent;

    protected Discount() {
    }

    public Discount(int price, int minimumOrderAmount) {
        this.price = price;
        this.percent = price / minimumOrderAmount;
    }
}
