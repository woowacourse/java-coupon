package coupon.coupon.domain;

import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import coupon.CouponException;

@Embeddable
public class CouponName {

    private static final int MAX_LENGTH = 30;
    private static final String NAME_LENGTH_MESSAGE = String.format("쿠폰은 %d자 이하의 이름을 설정해주세요.", MAX_LENGTH);

    @Column(nullable = false, length = MAX_LENGTH)
    private String name;

    protected CouponName() {
    }

    public CouponName(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        validateNull(name);
        validateNameLength(name);
    }

    private void validateNull(String name) {
        if (Objects.isNull(name)) {
            throw new CouponException("쿠폰 이름이 누락되었습니다.");
        }
    }

    private void validateNameLength(String name) {
        if (name.isBlank() || name.length() > MAX_LENGTH) {
            throw new CouponException(NAME_LENGTH_MESSAGE);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CouponName that = (CouponName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
