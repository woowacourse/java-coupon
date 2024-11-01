package coupon.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MemberNotFoundException extends RuntimeException {

    private static final String MESSAGE = "해당하는 회원을 찾을 수 없습니다.";

    private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public MemberNotFoundException() {
        super(MESSAGE);
    }
}
