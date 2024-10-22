package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PeriodTest {

    @Test
    @DisplayName("시작일이 종료일보다 이후일 때 예외 발생")
    void invalidPeriodCreation() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.minusDays(1);

        assertThatThrownBy(() -> new Period(startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작일은 종료일보다 이전이어야 합니다.");
    }
}
