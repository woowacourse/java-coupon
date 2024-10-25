package coupon.domain.coupon.discount;

import coupon.exception.DiscountPolicyValidationException;
import coupon.exception.DiscountPriceValidationException;
import jakarta.persistence.Embeddable;

@Embeddable
public class Discount {
    private static final int MIN_DISCOUNT_PRICE = 1_000;
    private static final int MAX_DISCOUNT_PRICE = 10_000;
    private static final int DISCOUNT_PRICE_UNIT = 500;

    private int discountPrice;
    private DiscountType discountType;
    private int minDiscountRange;
    private int maxDiscountRange;

    public Discount(DiscountType discountType, int discountPrice, int minDiscountRange, int maxDiscountRange) {
        validateDiscountPriceRange(discountPrice);
        validateDiscountPriceUnit(discountPrice);

        this.discountType = discountType;
        this.discountPrice = discountPrice;
        this.minDiscountRange = minDiscountRange;
        this.maxDiscountRange = maxDiscountRange;
    }

    public Discount() {
    }

    private void validateDiscountPriceRange(int price) {
        if (price < MIN_DISCOUNT_PRICE || price > MAX_DISCOUNT_PRICE) {
            throw new DiscountPriceValidationException(
                    "할인 가격은 %d원 이상 %d원 이하여야 합니다.".formatted(MIN_DISCOUNT_PRICE, MAX_DISCOUNT_PRICE)
            );
        }
    }

    private void validateDiscountPriceUnit(int price) {
        if (price % DISCOUNT_PRICE_UNIT != 0) {
            throw new DiscountPriceValidationException(
                    "할인 가격은 %d원 단위여야 합니다.".formatted(DISCOUNT_PRICE_UNIT)
            );
        }
    }

    public void validateDiscountPolicy(int minOrderPrice) {
        DiscountPolicy discountPolicy = discountType.createDiscountPolicy(minDiscountRange, maxDiscountRange);
        boolean isApprove = discountPolicy.validate(minOrderPrice, discountPrice);
        if (!isApprove) {
            throw new DiscountPolicyValidationException(minOrderPrice, discountPrice);
        }
    }

    public int getDiscountPrice() {
        return discountPrice;
    }
}
