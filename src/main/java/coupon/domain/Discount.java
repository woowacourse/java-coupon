package coupon.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class Discount {

	private int price;
	private int percent;

	public Discount(int price, int minimumOrderAmount) {
		this.price = price;
		this.percent = price / minimumOrderAmount;
	}

	protected Discount() {
	}
}
