package coupon.domain.usercoupon;

import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Getter;

@Getter
public class UsingDuration {

    private static final int DAYS = 7;

    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;

    public UsingDuration(LocalDateTime issuedDateTime) {
        this.startDateTime = issuedDateTime;
        this.endDateTime = issuedDateTime.plusDays(DAYS).with(LocalTime.MAX);
    }
}
