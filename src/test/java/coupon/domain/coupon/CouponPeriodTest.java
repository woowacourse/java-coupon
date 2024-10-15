package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDateTime;
import coupon.exception.CouponException;
import coupon.exception.ExceptionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponPeriodTest {

    @DisplayName("쿠폰 시작일과 종료일이 같을 수 있다.")
    @Test
    void createPeriodSameDateRange() {
        LocalDateTime startDate = LocalDateTime.of(2024, 10, 16, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 10, 16, 23, 59);

        assertDoesNotThrow(() -> new CouponPeriod(startDate, endDate));
    }

    @DisplayName("쿠폰 시작일이 종료일보다 늦으면 예외가 발생한다.")
    @Test
    void createPeriodInvalidDateRange() {
        LocalDateTime startDate = LocalDateTime.of(2024, 10, 16, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 10, 15, 23, 59);

        assertThatThrownBy(() -> new CouponPeriod(startDate, endDate))
                .isInstanceOf(CouponException.class)
                .hasMessage(ExceptionType.COUPON_DATE_INVALID.getMessage());
    }
}
