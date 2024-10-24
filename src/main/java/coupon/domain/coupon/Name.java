package coupon.domain.coupon;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Name {

    private static final int MAXIMUM_LENGTH = 30;

    private String name;

    public Name(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        validateExist(name);
        validateLength(name);
    }

    private void validateExist(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("쿠폰 이름이 존재하지 않습니다.");
        }
    }

    private void validateLength(String name) {
        if (name.length() > MAXIMUM_LENGTH) {
            throw new IllegalArgumentException("쿠폰 이름의 길이는 " + MAXIMUM_LENGTH + "자를 넘어갈 수 없습니다.");
        }
    }
}
