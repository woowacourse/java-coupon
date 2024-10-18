package coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PeriodTest {

    @Test
    @DisplayName("발급 기간을 알 수 있다.")
    void create() {
        LocalDate startDate = LocalDate.of(2024, 10, 18);
        LocalDate endDate = LocalDate.of(2024, 10, 18);

        Period period = new Period(startDate, endDate);

        assertThat(period).isEqualTo(new Period(startDate, endDate));
    }

    @Test
    @DisplayName("시작일이 종료일보다 이전이라면 예외가 발생한다.")
    void invalidPeriod() {
        LocalDate startDate = LocalDate.of(2024, 10, 19);
        LocalDate endDate = LocalDate.of(2024, 10, 18);

        assertThatThrownBy(() -> new Period(startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
