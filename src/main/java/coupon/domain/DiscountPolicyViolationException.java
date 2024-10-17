package coupon.domain;

public class DiscountPolicyViolationException extends RuntimeException {

    public DiscountPolicyViolationException(String message) {
        super(message);
    }
}
