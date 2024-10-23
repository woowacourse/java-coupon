package coupon.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GlobalCustomException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public GlobalCustomException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return errorMessage.getHttpStatus();
    }
}
