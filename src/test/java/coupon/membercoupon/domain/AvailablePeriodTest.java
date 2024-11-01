package coupon.membercoupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.coupon.domain.IssuedPeriod;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AvailablePeriodTest {

    @DisplayName("쿠폰 발급 시 사용 가능일이 종료일 보다 작을 때 발급일로부터 7일간 사용 가능하다")
    @Test
    void canAvailableEndDate() {
        // given
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusDays(9);
        LocalDateTime expected = LocalDate.now().plusDays(7).atStartOfDay().plusHours(23).plusMinutes(59)
                .plusSeconds(59).plusNanos(999999000);

        IssuedPeriod issuedPeriod = new IssuedPeriod(start, end);

        // when
        AvailablePeriod availablePeriod = new AvailablePeriod(issuedPeriod);
        LocalDateTime actual = availablePeriod.getEndDateTime();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("쿠폰의 사용 가능 기간을 검사할 수 있다.")
    @Test
    void isAvailable() {
        // given
        LocalDateTime start = LocalDateTime.now().minusDays(2);
        LocalDateTime end = LocalDateTime.now().plusDays(9);
        boolean expected = true;

        IssuedPeriod issuedPeriod = new IssuedPeriod(start, end);

        // when
        AvailablePeriod availablePeriod = new AvailablePeriod(issuedPeriod);
        boolean actual = availablePeriod.isAvailable();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("이미 발급일이 지난 쿠폰은 사용이 불가능하다.")
    @Test
    void canNotAvailablePeriod() {
        // given
        LocalDateTime start = LocalDateTime.now().minusDays(7);
        LocalDateTime end = LocalDateTime.now().minusDays(1);
        IssuedPeriod issuedPeriod = new IssuedPeriod(start, end);

        // when & then
        assertThatThrownBy(() -> new AvailablePeriod(issuedPeriod))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
