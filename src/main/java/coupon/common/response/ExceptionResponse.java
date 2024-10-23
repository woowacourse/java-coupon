package coupon.common.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionResponse {

    private String message;

    public static ExceptionResponse from(String message) {
        return new ExceptionResponse(message);
    }
}
