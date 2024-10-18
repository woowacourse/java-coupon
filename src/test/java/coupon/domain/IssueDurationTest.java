package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import coupon.domain.coupon.IssueDuration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IssueDurationTest {

    @Test
    @DisplayName("시작일이 종료일보다 늦으면 예외 발생")
    void givenStartDateIsAfterEndDate() {
        assertThatCode(() -> new IssueDuration(
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2024, 12, 31)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("발급 시작일이 종료일보다 늦을 수 없습니다.");
    }
}
