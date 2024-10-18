package coupon.domain.coupon.exception;

public class InvalidMinOrderMoneyException extends IllegalArgumentException {

    public InvalidMinOrderMoneyException() {
        super();
    }

    public InvalidMinOrderMoneyException(String s) {
        super(s);
    }

    public InvalidMinOrderMoneyException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMinOrderMoneyException(Throwable cause) {
        super(cause);
    }
}
