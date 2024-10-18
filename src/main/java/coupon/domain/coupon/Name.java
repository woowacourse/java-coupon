package coupon.domain.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Name {

    private static final int MAX_LENGTH = 30;

    @Column(name = "name", nullable = false)
    private String value;

    public Name(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        validateExist(value);
        validateLength(value);
    }

    private void validateExist(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("이름은 반드시 존재해야 합니다.");
        }
    }

    private void validateLength(String value) {
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("이름의 길이는 최대 " + MAX_LENGTH + "자 이하여야 합니다.");
        }
    }
}
