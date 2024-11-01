package coupon.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    public static final int ISSUED_COUPON_LIMIT = 5;

    /*
    회원에게 발급된 쿠폰은 발급일 포함 7일 동안 사용 가능하다. 만료 일의 23:59:59.999999 까지 사용할 수 있다.
     */
    private static final int EXPIRATION_DAYS = 7;

    private Long id;
    private Member member;
    private Coupon coupon;
    private Boolean used;
    private LocalDateTime issuedAt;
    private LocalDateTime expiredAt;

    private MemberCoupon(Member member, Coupon coupon, Boolean used, LocalDateTime issuedAt, LocalDateTime expiredAt) {
        this.member = member;
        this.coupon = coupon;
        this.used = used;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
    }

    public static MemberCoupon issue(Member member, Coupon coupon) {
        LocalDateTime issuedAt = LocalDateTime.now();
        return new MemberCoupon(member, coupon, false, issuedAt, getExpiredAt(issuedAt));
    }

    private static LocalDateTime getExpiredAt(LocalDateTime issuedAt) {
        return issuedAt.plusDays(EXPIRATION_DAYS)
                .minusDays(1)
                .with(LocalTime.MAX)
                .truncatedTo(ChronoUnit.MICROS);
    }
}
