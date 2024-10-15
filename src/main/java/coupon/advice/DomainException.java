package coupon.advice;

import org.springframework.http.HttpStatus;

public class DomainException extends CustomException {

    public DomainException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
