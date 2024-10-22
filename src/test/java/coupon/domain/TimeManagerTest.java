package coupon.domain;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TimeManagerTest {

    @DisplayName("같은 날에는 발급이 가능하다")
    @Test
    void isEffectiveSameDay() {
        TimeManager timeManager = new TimeManager(LocalDate.of(2024, 10, 22), LocalDate.of(2024, 10, 22));
        Assertions.assertThat(timeManager.isIssuable()).isTrue();
    }

    @DisplayName("이후의 날에 발급이 가능하다")
    @ParameterizedTest
    @CsvSource(value = {"2024-10-22","2024-10-23","2024-12-31","2042-1-1"},delimiter = '-')
    void isEffectiveAfter(int year, int month, int day) {
        TimeManager timeManager = new TimeManager(LocalDate.of(2024, 10, 22), LocalDate.of(year, month, day));
        Assertions.assertThat(timeManager.isIssuable()).isTrue();
    }


}
