package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    @Test
    void 쿠폰을_발급하면_만료일은_7일_뒤다() {
        // given
        LocalDateTime now = LocalDateTime.parse("2024-10-28T00:00:00");
        LocalDateTime end = LocalDateTime.parse("2024-11-04T23:59:59.999999");

        // when
        MemberCoupon memberCoupon = new MemberCoupon(1L, 1L, false, now);

        // then
        assertThat(memberCoupon.getIssueEndDate()).isEqualTo(end);
    }

}
