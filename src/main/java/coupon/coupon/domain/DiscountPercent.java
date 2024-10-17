package coupon.coupon.domain;

public record DiscountPercent(int percent) {

    private static final int MIN_PERCENT = 3;
    private static final int MAX_PERCENT = 20;

    public DiscountPercent {
        validate(percent);
    }

    private void validate(int percent) {
        if (percent < MIN_PERCENT) {
            throw new IllegalArgumentException("할인율은 " + MIN_PERCENT + "원 이상이어야 한다.");
        }
        if (percent > MAX_PERCENT) {
            throw new IllegalArgumentException("할인율은 " + MAX_PERCENT + "원 이하여야 한다.");
        }
    }
}
