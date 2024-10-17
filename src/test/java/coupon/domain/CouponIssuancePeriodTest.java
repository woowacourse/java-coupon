package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CouponIssuancePeriodTest {

    @DisplayName("시작일이 종료일보다 이후면, 예외를 발생한다.")
    @Test
    void testValidateDuration() {
        LocalDateTime startDate = LocalDateTime.of(2024, Month.OCTOBER, 18, 0, 0, 0, 2000);
        LocalDateTime endDate = LocalDateTime.of(2024, Month.OCTOBER, 18, 0, 0, 0, 1000);

        assertThatThrownBy(() -> new CouponIssuancePeriod(startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작일은 종료일보다 이전이어야 합니다.");
    }
}
