package coupon.exception;

public class CustomException extends RuntimeException {
    protected String message;

    public CustomException() {
        super();
    }

    public CustomException(String message) {
        super(message);
        this.message = message;
    }
}
