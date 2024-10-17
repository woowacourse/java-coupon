package coupon.coupon.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record Name(String name) {

    private static final int MAX_NAME_LENGTH = 30;

    public Name {
        validateNameLength(name);
    }

    public void validateNameLength(final String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("이름은 최대 30자 이하여야 합니다.");
        }
    }
}
