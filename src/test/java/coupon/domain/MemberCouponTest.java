package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    @Test
    void 만료일은_발급일_포함_7일_23시_59분_59초_999999까지다() {
        LocalDateTime now = LocalDateTime.parse("2024-10-20T13:30:00.123456");
        MemberCoupon coupon = new MemberCoupon(1L, 1L, now);

        LocalDateTime expected = LocalDateTime.parse("2024-10-26T23:59:59.999999");

        assertThat(coupon.getExpiredAt()).isEqualTo(expected);
    }
}
