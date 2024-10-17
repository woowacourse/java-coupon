package coupon.domain.coupon;

public class DiscountPolicyViolationException extends RuntimeException {

    public DiscountPolicyViolationException(String message) {
        super(message);
    }
}
