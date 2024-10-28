package coupon.domain.coupon;

import lombok.Getter;

@Getter
public class Name {

    private final String name;

    public Name(String name) {
        validateNameExists(name);
        this.name = name;
    }

    private void validateNameExists(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 필수 값입니다.");
        }
    }
}
