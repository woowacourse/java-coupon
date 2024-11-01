package coupon.membercoupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import coupon.membercoupon.domain.MemberCoupon;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    @Test
    void 회원_쿠폰_생성() {
        // given
        long couponId = 1L;
        long memberId = 1L;

        // when, then
        assertDoesNotThrow(() -> new MemberCoupon(couponId, memberId));
    }

    @Test
    void 회원_쿠폰이_생성되면_사용여부는_false로_설정() {
        // given
        long couponId = 1L;
        long memberId = 1L;
        LocalDateTime issuedAt = LocalDateTime.now();

        // when
        MemberCoupon memberCoupon = new MemberCoupon(couponId, memberId);
        boolean actual = memberCoupon.isUsed();

        // then
        assertThat(actual).isFalse();
    }

    @Test
    void 회원_쿠폰이_생성되면_만료일은_발급일_포함_7일로_설정() {
        // given
        long couponId = 1L;
        long memberId = 1L;
        LocalDateTime issuedAt = LocalDateTime.now();

        // when
        MemberCoupon memberCoupon = new MemberCoupon(couponId, memberId);
        LocalDateTime actual = memberCoupon.getExpiredAt();

        // then
        long expected = ChronoUnit.DAYS.between(issuedAt, actual);
        assertThat(expected).isEqualTo(6);
    }
}
