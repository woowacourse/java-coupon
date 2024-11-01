package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
public class CouponName implements Serializable {

    private static final int MAX_LENGTH = 30;

    @Column(name = "coupon_name")
    private String name;

    public CouponName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("쿠폰 이름은 필수값입니다.");
        }
        if (name.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("쿠폰 이름은 %d 자 이하여야 합니다.".formatted(MAX_LENGTH));
        }
        this.name = name;
    }
}
