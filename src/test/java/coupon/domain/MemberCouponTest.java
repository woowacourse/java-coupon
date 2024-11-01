package coupon.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class MemberCouponTest {

    @Test
    void 쿠폰을_사용_처리_한다() {
        // given
        MemberCoupon memberCoupon = new MemberCoupon(1L, 1L, 1L, false, LocalDateTime.now());

        // when
        memberCoupon.use();

        // then
        assertThat(memberCoupon.isUsed()).isTrue();
    }

    @Test
    void 이미_사용한_쿠폰은_사용할_수_없다() {
        // given
        MemberCoupon memberCoupon = new MemberCoupon(1L, 1L, 1L, true, LocalDateTime.now());

        // when & then
        assertThatThrownBy(() -> memberCoupon.use())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이미 사용한 쿠폰입니다. couponId : " + 1L);
    }

    @Test
    void 쿠폰_만료_일의_23시59분59초999999밀리초_까지_사용할_수_있다() {
        // given
        MemberCoupon memberCoupon = new MemberCoupon(1L, 1L, 1L, false, LocalDateTime.now().minusDays(7));

        // when & then
        assertThatThrownBy(() -> memberCoupon.use())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("만료된 쿠폰입니다. couponId : " + 1L);
    }

    @Test
    void 회원에게_발급된_쿠폰은_발급일_포함_7일_동안_사용_가능하다() {
        // given
        LocalDateTime issuedAt = LocalDateTime.now();
        MemberCoupon memberCoupon = new MemberCoupon(1L, 100L, 200L, false, issuedAt);

        // then
        LocalDateTime expectedExpiresAt = issuedAt.plusDays(6).with(LocalTime.MAX);
        assertThat(memberCoupon.getExpiresAt()).isEqualTo(expectedExpiresAt.toString());
    }
}
