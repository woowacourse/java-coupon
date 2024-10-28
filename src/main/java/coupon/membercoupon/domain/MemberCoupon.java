package coupon.membercoupon.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberCoupon {

    private boolean useable;

    private AvailablePeriod availablePeriod;

    boolean isUseable() {
        return useable && availablePeriod.isAvailable();
    }
}
