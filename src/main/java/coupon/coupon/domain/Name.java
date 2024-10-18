package coupon.coupon.domain;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class Name {

    private String name;

    public Name(String name) {
        validateLength(name);
        this.name = name;
    }

    private void validateLength(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name은 null이거나, 빈 값일 수 없습니다.");
        }
        if (name.length() > 30) {
            throw new IllegalArgumentException("name의 길이는 30자를 초과할 수 없습니다.");
        }
    }

    public String getName() {
        return name;
    }
}
