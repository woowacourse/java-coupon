package coupon.domain;

import coupon.exception.CouponException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

class CouponPeriodTest {

    @Test
    @DisplayName("종료일이 시작일보다 이전일 때 예외 발생")
    void validateDates_EndDateBeforeStartDate_ThrowsException() {
        // given
        LocalDateTime startDate = LocalDateTime.of(2024, 10, 10, 10, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 10, 1, 10, 0);

        // when & then
        Assertions.assertThatThrownBy(() -> new CouponPeriod(startDate, endDate))
                .isInstanceOf(CouponException.class)
                .hasMessage("종료일이 시작일보다 이전입니다.");
    }
}
