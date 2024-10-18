package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponName {

    private static final int MAX_LENGTH = 30;

    @NotNull
    @Column(length = 30)
    private String name;

    public CouponName(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        if (name.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("coupon name은 30자 이하여야 합니다.");
        }
    }
}
