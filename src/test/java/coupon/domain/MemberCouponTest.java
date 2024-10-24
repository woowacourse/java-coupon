package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    @Test
    @DisplayName("발급 받은 쿠폰은 7일간 이용이 가능하다.")
    void canUseFor7Days() {
        // given
        MemberCoupon memberCoupon = new MemberCoupon(1L, 1L);
        LocalDateTime expectedAt = LocalDateTime.now()
                .toLocalDate()
                .plusDays(7)
                .atStartOfDay()
                .minusNanos(1);

        // when
        LocalDateTime expiresAt = memberCoupon.getExpiresAt();

        // then
        assertThat(expiresAt).isEqualToIgnoringNanos(expectedAt);
    }
}
