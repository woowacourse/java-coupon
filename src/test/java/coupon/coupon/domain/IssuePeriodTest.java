package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IssuePeriodTest {

    @Test
    @DisplayName("실패 : 시작일, 종료일 존재하지 않음")
    void failNullStartPeriod() {
        assertThatThrownBy(() -> new IssuePeriod(LocalDateTime.now(), null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작일과 종료일은 반드시 존재해야 한다.");
    }

    @Test
    @DisplayName("실패 : 시작일, 종료일 존재하지 않음")
    void failNullEndPeriod() {
        assertThatThrownBy(() -> new IssuePeriod(null, LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작일과 종료일은 반드시 존재해야 한다.");
    }

    @Test
    @DisplayName("실패 : 종료일이 시작일보다 큼")
    void failBefore() {
        assertThatThrownBy(() -> new IssuePeriod(LocalDateTime.now(), LocalDateTime.now().minusDays(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작일은 종료일보다 이전이어야 한다.");
    }
}
