package coupon.domain.exception;

public class MinOrderPriceValidationException extends CouponException {
    public MinOrderPriceValidationException() {
        super();
    }

    public MinOrderPriceValidationException(int minimumMinOrderPrice, int maximumMinOrderPrice) {
        this("최소 주문 금액은 %d원 이상, %d원 이하여야 합니다.".formatted(minimumMinOrderPrice, maximumMinOrderPrice));
    }

    public MinOrderPriceValidationException(String message) {
        super(message);
    }

    public MinOrderPriceValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MinOrderPriceValidationException(Throwable cause) {
        super(cause);
    }

    public MinOrderPriceValidationException(String message, Throwable cause, boolean enableSuppression,
                                            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
