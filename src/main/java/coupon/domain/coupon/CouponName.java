package coupon.domain.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponName {

    @Column(name = "coupon_name", nullable = false, length = 30)
    private String value;

    public CouponName(String value) {
        this.value = value;
    }
}
