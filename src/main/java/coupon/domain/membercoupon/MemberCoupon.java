package coupon.domain.membercoupon;

import coupon.domain.coupon.Coupon;
import coupon.domain.member.Member;
import coupon.infra.db.MemberCouponEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberCoupon {

    private final Long id;

    private final Coupon coupon;

    private final Member member;

    private final boolean use;

    private final LocalDate startDate;

    private final LocalDate endDate;

    public static MemberCoupon of(MemberCouponEntity entity, Coupon coupon, Member member) {

        if (!member.isIdOf(entity.getMemberId()) || !coupon.isIdOf(entity.getCouponId())) {
            throw new IllegalArgumentException("매개변수간 일관성이 지켜지지 않았습니다.");
        }

        return new MemberCoupon(
                entity.getId(),
                coupon,
                member,
                entity.isUse(),
                entity.getStartDate(),
                entity.getEndDate()
        );
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

    public Long getCouponId() {
        return coupon.getId();
    }

    public Long getMemberId() {
        return member.getId();
    }
}
