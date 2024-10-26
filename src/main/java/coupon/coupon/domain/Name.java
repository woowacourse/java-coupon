package coupon.coupon.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record Name(String name) {

    private static final int MAX_NAME_LENGTH = 30;

    public Name {
        validateNameNull(name);
        validateNameLength(name);
    }

    private void validateNameNull(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("이름은 null이 될 수 없습니다.");
        }
    }

    private void validateNameLength(final String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("이름은 최대 30자 이하여야 합니다.");
        }
    }
}
