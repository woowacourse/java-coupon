package coupon.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class Discount {

	private int price;
	private int percent;

	public Discount(int price, int minimumOrderAmount) {
		validatePrice(price);
		this.price = price;
		this.percent = price / minimumOrderAmount;
		validatePercent(percent);
	}

	protected Discount() {
	}

	private void validatePrice(int price) {
		if(price < 5000 || price > 100000) {
			throw new IllegalArgumentException("금액은 5,000원 이상, 100,000원 이하 부터 가능합니다.");
		}
	}

	private void validatePercent(int price) {
		if(percent < 3 || percent > 20) {
			throw new IllegalArgumentException("할인율은 3% 이상, 20% 이하여야 합니다.");
		}
	}
}
