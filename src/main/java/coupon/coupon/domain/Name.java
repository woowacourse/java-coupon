package coupon.coupon.domain;

import java.util.Objects;

public class Name {

    private static final int MAX_LENGTH_OF_NAME = 30;

    private final String name;

    public Name(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("쿠폰의 이름은 비어있을 수 없습니다.");
        }
        if (name.length() > MAX_LENGTH_OF_NAME) {
            throw new IllegalArgumentException("쿠폰 이름은 %d자 이하여야 합니다.".formatted(MAX_LENGTH_OF_NAME));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name1 = (Name) o;
        return Objects.equals(name, name1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
