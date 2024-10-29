package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public class CouponName {

    @Column(name = "name", nullable = false)
    private String name;

    public CouponName(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        if (name == null || name.isBlank() || name.length() > 30) {
            throw new IllegalArgumentException("쿠폰의 이름은 1자 이상 30자 이하여야 합니다.");
        }
    }
}
