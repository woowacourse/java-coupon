package coupon.domain;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CouponName implements Serializable {

    private static final int MAXIMUM_VALID_LENGTH = 30;

    private String name;

    protected CouponName() {
    }

    public CouponName(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("쿠폰 이름은 필수입니다.");
        }
        if (name.length() > MAXIMUM_VALID_LENGTH) {
            throw new IllegalArgumentException("쿠폰 이름은 30자 이하여야 합니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CouponName that = (CouponName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }
}
