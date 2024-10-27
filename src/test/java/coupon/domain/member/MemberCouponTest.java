package coupon.domain.member;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    @DisplayName("발급된 쿠폰이 만료되었는지 확인한다.")
    @Test
    void isExpired() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime later = now.plusDays(7);
        MemberCoupon memberCoupon = new MemberCoupon(0, 0, 0, false, now, later);

        Assertions.assertThat(memberCoupon.isExpired()).isTrue();
    }
}
