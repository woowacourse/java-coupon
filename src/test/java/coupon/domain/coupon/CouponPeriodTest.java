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
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now();

        assertDoesNotThrow(() -> new CouponPeriod(startDate, endDate));
    }

    @DisplayName("쿠폰 시작일이 종료일보다 늦으면 예외가 발생한다.")
    @Test
    void createPeriodInvalidDateRange() {
        LocalDateTime startDate = LocalDateTime.now().plusDays(1);
        LocalDateTime endDate = LocalDateTime.now();

        assertThatThrownBy(() -> new CouponPeriod(startDate, endDate))
                .isInstanceOf(CouponException.class)
                .hasMessage(ExceptionType.COUPON_DATE_INVALID.getMessage());
    }
}
