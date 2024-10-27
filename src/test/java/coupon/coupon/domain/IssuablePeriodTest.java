package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IssuablePeriodTest {

    @DisplayName("시작일이 종료일 이후인 경우, 예외가 발생한다.")
    @Test
    void validateTime() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.minusDays(1L);

        assertThatThrownBy(() -> new IssuablePeriod(startTime, endTime))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작일은 종료일보다 이전이어야 합니다.");
    }
}
