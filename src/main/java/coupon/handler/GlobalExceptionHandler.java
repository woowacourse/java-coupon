package coupon.handler;

import coupon.common.exception.CouponException;
import coupon.common.exception.CouponNotFoundException;
import coupon.common.response.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CouponException.class)
    private ResponseEntity<ExceptionResponse> couponException(
            HttpServletRequest request,
            CouponException e
    ) {
        logHandlerError(request, e.getMessage());
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ExceptionResponse.from(e.getErrorConstant().getMessage()));
    }

    @ExceptionHandler(CouponNotFoundException.class)
    private ResponseEntity<ExceptionResponse> couponNotFoundException(
            HttpServletRequest request,
            CouponNotFoundException e
    ) {
        logHandlerError(request, e.getMessage());
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ExceptionResponse.from(e.getMessage()));
    }

    private void logHandlerError(HttpServletRequest request, String message) {
        log.error("message : {} URL : {} method : {} query : {} ",
                message,
                request.getRequestURL(),
                request.getMethod(),
                request.getQueryString()
        );
    }
}
