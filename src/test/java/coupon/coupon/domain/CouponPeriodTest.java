package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class CouponPeriodTest {

    @Test
    void 쿠폰의_종료일이_시작일보_전인경우_예외를_발생한다() {
        // given
        LocalDateTime startAt = LocalDateTime.of(1, 1, 1, 1, 1, 1);

        // when & then
        assertThatCode(() -> new CouponPeriod(startAt, startAt.minusDays(1)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
