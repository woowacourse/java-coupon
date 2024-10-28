package coupon.membercoupon.domain;

import static org.assertj.core.api.Assertions.assertThat;

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
}
