package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    @Test
    @DisplayName("쿠폰의 발급 기간은 발급일 포함 7일 동안 사용 가능하다.")
    void validateDuration() {
        MemberCoupon memberCoupon = MemberCoupon.issue(1L, 1L);
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime expiresAt = memberCoupon.getExpiresAt();

        LocalDateTime expected = today.plusDays(7);
        assertThat(expiresAt.getDayOfMonth()).isEqualTo(expected.getDayOfMonth());
    }
}
