package coupon.membercoupon.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberCouponTest {

    @DisplayName("멤버 쿠폰의 만료일은 발급일보다 6일 뒤이다.")
    @Test
    void testDuration() {
        // given & when
        MemberCoupon memberCoupon = new MemberCoupon(1L, 1L);

        // then
        LocalDateTime issuedAt = memberCoupon.getIssuedAt();
        LocalDateTime expiresAt = memberCoupon.getExpiresAt();

        Duration duration = Duration.between(issuedAt, expiresAt);
        assertThat(duration.toDays()).isEqualTo(6);
    }
}
