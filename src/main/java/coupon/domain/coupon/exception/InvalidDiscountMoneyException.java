package coupon.domain.coupon.exception;

public class InvalidDiscountMoneyException extends IllegalArgumentException {

    public InvalidDiscountMoneyException() {
        super();
    }

    public InvalidDiscountMoneyException(String s) {
        super(s);
    }

    public InvalidDiscountMoneyException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDiscountMoneyException(Throwable cause) {
        super(cause);
    }
}
