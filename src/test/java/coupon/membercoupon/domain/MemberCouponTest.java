package coupon.membercoupon.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

class MemberCouponTest {

    @DisplayName("현재 발급된 쿠폰은 7일 뒤 자정에 만료된다.")
    @Test
    void expiredTest() {
        // given
        LocalDateTime expected = LocalDate.now().plusDays(MemberCoupon.USEABLE_DAYS).atTime(LocalTime.MIN);

        // when
        MemberCoupon memberCoupon = new MemberCoupon(1L, 1L);

        // then
        Assertions.assertThat(memberCoupon.getExpiredAt()).isEqualTo(expected);
    }
}
