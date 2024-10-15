package coupon.advice;

import org.springframework.http.HttpStatus;

public class BadRequestException extends CustomException {

    public BadRequestException(String message, HttpStatus status) {
        super(message, status);
    }
}
