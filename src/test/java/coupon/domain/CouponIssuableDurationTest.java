package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import coupon.CouponIssuableDuration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponIssuableDurationTest {

    @Test
    @DisplayName("쿠폰 발급 가능 기간을 생성한다.")
    void create() {
        LocalDate now = LocalDate.now();
        LocalDate yesterday = now.minusDays(1);

        assertThatCode(() -> new CouponIssuableDuration(yesterday, now))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("시작일은 종료일보다 이전이어야 한다.")
    void invalidDuration() {
        LocalDate now = LocalDate.now();
        LocalDate yesterday = now.minusDays(1);

        assertThatThrownBy(() -> new CouponIssuableDuration(now, yesterday))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작일은 종료일보다 이전이어야 합니다.");
    }
}
