package coupon.coupons.domain;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import coupon.coupons.domain.Duration;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DurationTest {

    @DisplayName("발급 시작일이 존재하지 않는 경우 예외가 발생한다")
    @Test
    void validateStartDateIsNull() {
        assertThatThrownBy(() -> new Duration(null, LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("발급 시작일은 반드시 존재해야 합니다.");
    }

    @DisplayName("발급 종료일이 존재하지 않는 경우 예외가 발생한다")
    @Test
    void validateEndDateIsNull() {
        assertThatThrownBy(() -> new Duration(LocalDateTime.now(), null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("발급 종료일은 반드시 존재해야 합니다.");
    }

    @DisplayName("발급기간의 종료일이 시작일보다 빠를 경우 예외가 발생한다")
    @Test
    void validateDates() {
        assertThatThrownBy(() -> new Duration(LocalDateTime.now(), LocalDateTime.now().minusDays(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("종료일은 시작일보다 이후여야 합니다.");
    }
}
