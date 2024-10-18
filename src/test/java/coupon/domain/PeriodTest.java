package coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PeriodTest {

    @Test
    @DisplayName("발급 기간을 알 수 있다.")
    void create() {
        LocalDateTime startDate = LocalDateTime.of(2024, 10, 18, 0, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 10, 18, 23, 59, 59, 999999);

        Period period = new Period(startDate, endDate);

        assertThat(period).isEqualTo(new Period(startDate, endDate));
    }

    @Test
    @DisplayName("시작일이 종료일보다 이전이라면 예외가 발생한다.")
    void invalidPeriod() {
        LocalDateTime startDate = LocalDateTime.of(2024, 10, 18, 23, 59, 59);
        LocalDateTime endDate = LocalDateTime.of(2024, 10, 18, 0, 0, 0);

        assertThatThrownBy(() -> new Period(startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
