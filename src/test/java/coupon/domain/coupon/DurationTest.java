package coupon.domain.coupon;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DurationTest {

    @DisplayName("시작일이 종료일과 같거나 이전이면 예외가 발생하지 않는다")
    @CsvSource(value = {"2024-10-22", "2024-10-23", "2025-12-31", "2042-01-01"}, delimiter = '-')
    @ParameterizedTest
    void timeMangerConstruct(int year, int month, int day) {
        Assertions.assertThatCode(() -> new Duration(
                        LocalDate.of(2024, 10, 22),
                        LocalDate.of(year, month, day)))
                .doesNotThrowAnyException();
    }

    @DisplayName("시작날이 종료일보다 이후면 예외가 발생한다")
    @CsvSource(value = {"2024-01-22", "2024-05-23", "1995-12-31", "2004-01-10"}, delimiter = '-')
    @ParameterizedTest
    void timeMangerConstructException(int year, int month, int day) {
        Assertions.assertThatThrownBy(() -> new Duration(LocalDate.of(2024, 10, 22),
                        LocalDate.of(year, month, day)))
                .isInstanceOf(DateTimeException.class);
    }

    @DisplayName("쿠폰이 날짜 기간 내라면 발행이 가능하다.")
    @CsvSource(value = {"2024-10-22", "2024-12-31", "2024-11-01"}, delimiter = '-')
    @ParameterizedTest
    void isIssuable(int year, int month, int day) {
        Duration duration = new Duration(LocalDate.of(2024, 10, 22), LocalDate.of(2024, 12, 31));
        Assertions.assertThat(duration.isIssuable(LocalDateTime.of(year, month, day, 0, 0))).isTrue();
    }

    @DisplayName("쿠폰이 날짜 기간 밖이라면 발행이 불가능하다.")
    @CsvSource(value = {"1995-04-10", "2004-01-01", "2024-10-21","2025-01-01"}, delimiter = '-')
    @ParameterizedTest
    void isIssuableFalse(int year, int month, int day) {
        Duration duration = new Duration(LocalDate.of(2024, 10, 22), LocalDate.of(2024, 12, 31));
        Assertions.assertThat(duration.isIssuable(LocalDateTime.of(year, month, day, 0, 0))).isFalse();
    }

}
