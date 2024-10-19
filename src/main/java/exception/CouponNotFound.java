package exception;

public class CouponNotFound extends RuntimeException {

    public CouponNotFound() {
        super();
    }

    public CouponNotFound(String message) {
        super(message);
    }
}
