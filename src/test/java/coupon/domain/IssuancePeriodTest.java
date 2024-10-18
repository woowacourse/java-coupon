package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IssuancePeriodTest {

    @Test
    @DisplayName("시작일이 종료일보다 이후라면 예외가 발생한다.")
    void invalid_period() {
        //given
        final LocalDate startDate = LocalDate.now();
        final LocalDate endDate = startDate.minusDays(1);

        //when && then
        assertThatThrownBy(() -> new IssuancePeriod(startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("올바른 기간으로 발급 기간을 생성한다.")
    void valid_period() {
        //given
        final LocalDate startDate = LocalDate.now();
        final LocalDate endDate = startDate.plusDays(10);

        //when & then
        assertThatCode(() -> new IssuancePeriod(startDate, endDate))
                .doesNotThrowAnyException();
    }
}
