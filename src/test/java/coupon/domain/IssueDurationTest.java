package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.coupon.IssueDuration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IssueDurationTest {

    @DisplayName("시작 시점이 종료 시점보다 이후일 수 없다")
    @Test
    void throwIllegalArgumentException_When_StartTimeIsAfterThanEndTime() {
        LocalDateTime startAt = LocalDateTime.now();
        LocalDateTime endAt = startAt.minusNanos(1L);

        assertThatThrownBy(() -> new IssueDuration(startAt, endAt))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("쿠폰 만료기간을 설정할 수 있다")
    @Test
    void createIssueDuration() {
        LocalDateTime startAt = LocalDateTime.now();
        LocalDateTime endAt = startAt.plusNanos(1L);

        assertThatCode(() -> new IssueDuration(startAt, endAt))
                .doesNotThrowAnyException();
    }
}
