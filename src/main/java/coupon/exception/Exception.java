package coupon.exception;

public class Exception extends RuntimeException {
    protected String message;

    public Exception() {
    }

    public Exception(String message) {
        this.message = message;
    }
}
