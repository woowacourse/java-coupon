package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class DatePeriodTest {

    @Test
    void 시작일과_종료일을_통해_발급_가능_기간을_생성한다() {
        LocalDate startDate = LocalDate.of(2021, 1, 1);
        LocalDate endDate = LocalDate.of(2021, 1, 31);
        assertThatCode(() -> new DatePeriod(startDate, endDate))
                .doesNotThrowAnyException();
    }

    @Test
    void 발급일이_만료일보다_이후인_경우_예외를_발생한다() {
        LocalDate startDate = LocalDate.of(2021, 1, 31);
        LocalDate endDate = LocalDate.of(2021, 1, 30);
        assertThrows(IllegalArgumentException.class, () -> new DatePeriod(startDate, endDate));
    }

    @Test
    void 발급일과_만료일이_같은_경우_해당_일에만_발급할_수_있다() {
        LocalDate date = LocalDate.of(2021, 1, 31);
        LocalDate startDate = date;
        LocalDate endDate = date;
        DatePeriod datePeriod = new DatePeriod(startDate, endDate);
        assertAll(
                () -> assertThat(datePeriod.isBetweenPeriod(date.atTime(0, 0))).isTrue(),
                () -> assertThat(datePeriod.isBetweenPeriod(date.plusDays(1).atStartOfDay())).isFalse()
        );
    }
}
