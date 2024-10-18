package coupon.membercoupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    @Test
    void 회원_쿠폰_생성() {
        // given
        long couponId = 1L;
        long memberId = 1L;
        LocalDateTime issuedAt = LocalDateTime.now();

        // when, then
        assertDoesNotThrow(() -> new MemberCoupon(couponId, memberId, issuedAt));
    }

    @Test
    void 회원_쿠폰이_생성되면_사용여부는_false로_설정() {
        // given
        long couponId = 1L;
        long memberId = 1L;
        LocalDateTime issuedAt = LocalDateTime.now();

        // when
        MemberCoupon memberCoupon = new MemberCoupon(couponId, memberId, issuedAt);
        boolean actual = memberCoupon.isUsed();

        // then
        assertThat(actual).isFalse();
    }

    @Test
    void 회원_쿠폰이_생성되면_만료일은_발급일로부터_7일로_설정() {
        // given
        long couponId = 1L;
        long memberId = 1L;
        LocalDateTime issuedAt = LocalDateTime.now();

        // when
        MemberCoupon memberCoupon = new MemberCoupon(couponId, memberId, issuedAt);
        LocalDateTime actual = memberCoupon.getExpiredAt();

        // then
        long expected = ChronoUnit.DAYS.between(issuedAt, actual);
        assertThat(expected).isEqualTo(7);
    }

    @Test
    void 이미_지난날을_발급일로_설정할_경우_예외() {
        // given
        long couponId = 1L;
        long memberId = 1L;
        LocalDateTime issuedAt = LocalDateTime.now().minusDays(1);

        // when, then
        assertThatThrownBy(() -> new MemberCoupon(couponId, memberId, issuedAt));
    }
}
