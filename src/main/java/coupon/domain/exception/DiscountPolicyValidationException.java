package coupon.domain.exception;

public class DiscountPolicyValidationException extends CouponException {
    private static final String MESSAGE = "할인 정책에 위반되는 설정값입니다. (minOrderPrice : %d | discountPrice : %d)";

    public DiscountPolicyValidationException() {
        super();
    }

    public DiscountPolicyValidationException(int minOrderPrice, int discountPrice) {
        this(MESSAGE.formatted(minOrderPrice, discountPrice));
    }
    public DiscountPolicyValidationException(String message) {
        super(message);
    }

    public DiscountPolicyValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DiscountPolicyValidationException(Throwable cause) {
        super(cause);
    }

    public DiscountPolicyValidationException(String message, Throwable cause, boolean enableSuppression,
                                             boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
