package coupon.membercoupon.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberCoupon {

    private boolean available;

    private AvailablePeriod availablePeriod;

    boolean isUseable() {
        return available && availablePeriod.isAvailable();
    }

    public LocalDateTime getAvailableStartDate() {
        return availablePeriod.getStartDateTime();
    }

    public LocalDateTime getAvailableEndDate() {
        return availablePeriod.getEndDateTime();
    }
}
