package coupon.membercoupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.coupon.domain.IssuedPeriod;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AvailablePeriodTest {

    @DisplayName("시작일이 발급일 보다 늦으면 쿠폰을 발급할 수 없다")
    @Test
    void validateStartDate() {
        // given
        LocalDateTime start = LocalDateTime.now().plusSeconds(1);
        LocalDateTime end = LocalDateTime.now().plusSeconds(8);

        IssuedPeriod issuedPeriod = new IssuedPeriod(start, end);

        // when
        assertThatThrownBy(() -> new AvailablePeriod(issuedPeriod))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("만료일이 발급일 보다 늦으면 쿠폰을 발급할 수 없다")
    @Test
    void validateEndDate() {
        // given
        LocalDateTime start = LocalDateTime.now().minusSeconds(2);
        LocalDateTime end = LocalDateTime.now().minusSeconds(1);

        IssuedPeriod issuedPeriod = new IssuedPeriod(start, end);

        // when
        assertThatThrownBy(() -> new AvailablePeriod(issuedPeriod))
                .isInstanceOf(IllegalArgumentException.class);
    }

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
}
