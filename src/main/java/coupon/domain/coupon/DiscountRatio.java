package coupon.domain.coupon;


public class DiscountRatio {
    private static final int MIN_SALE_RATIO = 3;
    private static final int MAX_SALE_RATIO = 20;

    private int ratio;

    public DiscountRatio(int ratio) {
        validateDiscountRatio(ratio);
        this.ratio = ratio;
    }

    protected DiscountRatio() {
    }

    public static void validateDiscountRatio(int ratio) {
        if (ratio < MIN_SALE_RATIO || ratio > MAX_SALE_RATIO) {
            throw new IllegalArgumentException("할인율은 " + MIN_SALE_RATIO + "이상 " + MAX_SALE_RATIO + "이하여야 합니다");
        }
    }

    public int getRatio() {
        return ratio;
    }
}
