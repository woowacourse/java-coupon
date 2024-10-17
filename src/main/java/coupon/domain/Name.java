package coupon.domain;

import jakarta.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public class Name {

    private static final int MAX_NAME_LENGTH = 30;

    private String name;

    public Name(String name) {
        validateNameLength(name);
        this.name = name;
    }

    public void validateNameLength(String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("이름은 최대 30자 이하여야 합니다.");
        }
    }
}
