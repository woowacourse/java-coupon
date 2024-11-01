package coupon.entity.coupon;

import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class CouponIssuancePeriod {

    private LocalDate issuanceStartDate;
    private LocalDate issuanceEndDate;

    public CouponIssuancePeriod(LocalDate issuanceStartDate, LocalDate issuanceEndDate) {
        this.issuanceStartDate = issuanceStartDate;
        this.issuanceEndDate = issuanceEndDate;
    }
}
