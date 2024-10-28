package coupon.membercoupon.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    @Test
    @DisplayName("만료일은 발급일을 포함하여 7일 후 23시 59분 59.999999초로 설정된다.")
    void should_expired_at_is_issued_at_plus_7_days_minus_1000_nanos_second() {
        // given
        LocalDateTime issuedAt = LocalDateTime.parse("2024-10-28T00:00:00");
        LocalDateTime expected = LocalDateTime.parse("2024-11-03T23:59:59.999999");

        // when
        MemberCoupon coupon = new MemberCoupon(1L, 1L, issuedAt);

        // then
        assertThat(coupon.getExpiredAt()).isEqualTo(expected);
    }
}
