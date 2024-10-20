package coupon.coupon.domain;

import lombok.Getter;

@Getter
public class DiscountPercent {

    private static final double MIN_PERCENT = 3;
    private static final double MAX_PERCENT = 20;
    private final double percent;


    public DiscountPercent(int discountPrice, int minimumOrderPrice) {
        double percent = (double) discountPrice / minimumOrderPrice * 100;
        validate(percent);
        this.percent = percent;
    }

    private void validate(double percent) {
        if (percent < MIN_PERCENT) {
            throw new IllegalArgumentException("할인율은 " + MIN_PERCENT + "% 이상이어야 한다.");
        }
        if (percent > MAX_PERCENT) {
            throw new IllegalArgumentException("할인율은 " + MAX_PERCENT + "% 이하여야 한다.");
        }
    }
}
