package coupon.coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DatePeriodTest {

    @DisplayName("시작 날짜가 종료 날짜보다 뒤에 있으면 예외를 발생시킨다")
    @Test
    void startDateIsNotLaterEndDate() {
        LocalDate today = LocalDate.now();
        assertThatThrownBy(() -> new DatePeriod(today, today.minusDays(8)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.START_DATE_BEFORE_END_DATE_EXCEPTION.getMessage());
    }
}
