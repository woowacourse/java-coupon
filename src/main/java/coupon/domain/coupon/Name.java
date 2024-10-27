package coupon.domain.coupon;

import coupon.exception.NameLengthExceedException;
import coupon.exception.EmptyNameException;
import lombok.Getter;

@Getter
class Name {
    private static final int MAX_LENGTH = 30;

    private final String value;

    public Name(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new EmptyNameException();
        }
        if (value.length() > MAX_LENGTH) {
            throw new NameLengthExceedException(String.valueOf(MAX_LENGTH));
        }
    }
}
