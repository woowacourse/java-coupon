package coupon.domain.membercoupon;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MemberCoupon {

    private final Long id;

    private final Long couponId;

    private final Long memberId;

    private final boolean use;

    private final LocalDate startDate;

    private final LocalDate endDate;

    public MemberCoupon(Long id, Long couponId, Long memberId, boolean use, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.couponId = couponId;
        this.memberId = memberId;
        this.use = use;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isUsable(LocalDateTime base) {
        LocalDateTime startDateTimeExclude = LocalDateTime.of(
                startDate.minusDays(1),
                LocalTime.of(23, 59, 59, 999999999)
        );
        LocalDateTime endDateTimeExclude = LocalDateTime.of(
                endDate.plusDays(1),
                LocalTime.of(0, 0, 0, 0)
        );
        return !use && base.isAfter(startDateTimeExclude) && base.isBefore(endDateTimeExclude);
    }
}
