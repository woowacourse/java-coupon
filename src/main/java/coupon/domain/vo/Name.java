package coupon.domain.vo;

import coupon.exception.ErrorMessage;
import coupon.exception.GlobalCustomException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Name {

    private static final int MAX_LENGTH = 30;

    @Column(name = "name", nullable = false, length = 30)
    private String value;

    public Name(String value) {
        validateValue(value);
        this.value = value;
    }

    private void validateValue(String name) {
        if (name == null || name.isBlank() || name.length() > MAX_LENGTH) {
            throw new GlobalCustomException(ErrorMessage.INVALID_COUPON_NAME);
        }
    }
}
