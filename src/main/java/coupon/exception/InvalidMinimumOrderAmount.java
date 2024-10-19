package coupon.exception;

public class InvalidMinimumOrderAmount extends RuntimeException {

    public InvalidMinimumOrderAmount(final String message) {
        super(message);
    }
}
