package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PeriodTest {

    @Test
    @DisplayName("쿠폰 기간을 생성한다.")
    void create_coupon_period() {
        // given
        final LocalDateTime startAt = LocalDateTime.of(2024, 1, 1, 1, 0);
        final LocalDateTime endAt = LocalDateTime.of(2024, 1, 2, 1, 1, 0);

        // when & then
        assertThatCode(() -> new Period(startAt, endAt))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("쿠폰 시작이 종료일보다 뒤일 수 없습니다.")
    void coupon_end_date_can_not_before_start_date() {
        // given
        final LocalDateTime startAt = LocalDateTime.of(2024, 1, 2, 1, 1, 0);
        final LocalDateTime endAt = LocalDateTime.of(2024, 1, 1, 1, 0);

        // when & then
        assertThatThrownBy(() -> new Period(startAt, endAt))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
