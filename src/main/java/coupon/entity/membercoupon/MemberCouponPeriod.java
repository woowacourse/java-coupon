package coupon.entity.membercoupon;

import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class MemberCouponPeriod {

    private LocalDate issuedAt;
    private LocalDate expiredAt;

    public MemberCouponPeriod(LocalDate issuedAt) {
        this.issuedAt = issuedAt;
        this.expiredAt = issuedAt.plusDays(7);
    }
}
