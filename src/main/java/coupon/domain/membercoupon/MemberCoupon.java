package coupon.domain.membercoupon;

import coupon.domain.coupon.Coupon;
import coupon.domain.member.Member;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberCoupon {

    private final Long id;

    private final Coupon coupon;

    private final Member member;

    private final boolean use;

    private final LocalDate startDate;

    private final LocalDate endDate;

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
