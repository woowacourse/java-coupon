package coupon.coupon.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IssuedPeriodTest {

    @DisplayName("시작일은 종료일보다 이전이어야 한다.")
    @Test
    void validateIssuedPeriod() {
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        LocalDateTime today = LocalDateTime.now();
        assertThatThrownBy(() -> new IssuedPeriod(tomorrow, today))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("시작일과 종료일이 같다면, 해당 일에만 발급할 수 있는 쿠폰이 된다")
    @Test
    void validateIssuedPeriodEqualStartEndDate() {
        // given
        LocalDate today = LocalDate.now();
        LocalDateTime expectedStartDatetime = LocalDate.now().atStartOfDay();
        LocalDateTime expectedEndDateTime = LocalDate.now().atStartOfDay()
                .plusHours(23)
                .plusMinutes(59)
                .plusSeconds(59)
                .plusNanos(999999000);

        // when
        IssuedPeriod issuedPeriod = new IssuedPeriod(today);
        LocalDateTime actualStartDateTime = issuedPeriod.getStartDateTime();
        LocalDateTime actualEndDateTime = issuedPeriod.getEndDateTime();

        // then
        assertAll(
                () -> assertThat(actualStartDateTime).isEqualTo(expectedStartDatetime),
                () -> assertThat(actualEndDateTime).isEqualTo(expectedEndDateTime)
        );
    }
}

