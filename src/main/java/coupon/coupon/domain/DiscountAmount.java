package coupon.coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class DiscountAmount {

    private static final int MIN_DISCOUNT_AMOUNT = 1000;
    private static final int MAX_DISCOUNT_AMOUNT = 10000;
    private static final int DISCOUNT_UNIT = 500;
    private static final int MIN_DISCOUNT_RATE = 3;
    private static final int MAX_DISCOUNT_RATE = 20;

    @Column(name = "discount_amount")
    private int value;

    public DiscountAmount(){
    }

    public DiscountAmount(int discountAmount, int minimumOrderAmount) {
        validateAmount(discountAmount);
        validateUnit(discountAmount);
        validateDiscountRate(discountAmount, minimumOrderAmount);
        this.value = discountAmount;
    }

    private void validateAmount(int value) {
        if (value < MIN_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException("할인 금액은 1,000원 이상이어야 합니다.");
        }
        if (value > MAX_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException("할인 금액은 10,000원 이하여야 합니다.");
        }
    }

    private void validateUnit(int value) {
        if (value % DISCOUNT_UNIT != 0) {
            throw new IllegalArgumentException("할인 금액은 500원 단위로만 설정할 수 있습니다.");
        }
    }

    private void validateDiscountRate(int discountAmount, int minimumOrderAmount) {
        int discountRate = discountAmount * 100 / minimumOrderAmount;
        System.out.println(discountRate);
        if (discountRate < MIN_DISCOUNT_RATE) {
            throw new IllegalArgumentException("할인율은 3% 이상이어야 합니다.");
        }
        if (discountRate > MAX_DISCOUNT_RATE) {
            throw new IllegalArgumentException("할인율은 20% 이하여야 합니다.");
        }
    }

    public int getValue() {
        return value;
    }
}
