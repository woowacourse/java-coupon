package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class CouponPeriodTest {

    @Test
    void 쿠폰의_시작일이_종료일보다_뒤인경우_예외를_발생한다() {
        // given
        LocalDateTime endDate = LocalDateTime.of(1, 1, 1, 1, 1, 1);

        // when & then
        assertThatCode(() -> new CouponPeriod(endDate.plusDays(1), endDate))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
