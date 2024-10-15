package coupon.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ExceptionType {

    COUPON_NAME_BLANK(HttpStatus.BAD_REQUEST, "쿠폰 이름을 입력해주세요."),
    COUPON_NAME_LENGTH_EXCEED(HttpStatus.BAD_REQUEST, "쿠폰 이름은 30자 이하여야합니다.");

    private final HttpStatus status;
    private final String message;
}
