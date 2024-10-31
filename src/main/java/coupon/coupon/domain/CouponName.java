package coupon.coupon.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponName {

    private static final int MAX_LENGTH_OF_NAME = 30;

    private String name;

    public CouponName(String name) {
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

    public String getValue() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CouponName name1 = (CouponName) o;
        return Objects.equals(name, name1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
