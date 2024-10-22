package coupon.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    /*
    회원에게 발급된 쿠폰은 발급일 포함 7일 동안 사용 가능하다. 만료 일의 23:59:59.999999 까지 사용할 수 있다.
     */
    private static final int EXPIRATION_DAYS = 7;

    private Long id;
    private Long memberId;
    private Long couponId;
    private Boolean used;
    private LocalDateTime issuedAt;
    private LocalDateTime expiredAt;

    public MemberCoupon(Member member, Coupon coupon) {
        this.memberId = member.getId();
        this.couponId = coupon.getId();
        this.used = false;
        this.issuedAt = LocalDateTime.now();
        this.expiredAt = issuedAt.plusDays(EXPIRATION_DAYS).minusDays(1).with(LocalTime.MAX)
                .truncatedTo(ChronoUnit.MICROS);
    }
}
