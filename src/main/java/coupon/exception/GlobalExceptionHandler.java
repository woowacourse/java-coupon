package coupon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GlobalCustomException.class)
    public ResponseEntity<ProblemDetail> handleGlobalCustomException(GlobalCustomException e) {
        HttpStatus httpStatus = e.getHttpStatus();
        return ResponseEntity.status(httpStatus)
                .body(ProblemDetail.forStatusAndDetail(httpStatus, e.getMessage()));
    }
}
