package coupon.domain.coupon;

import coupon.common.ErrorConstant;
import coupon.common.exception.CouponException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IssuePeriodTest {

    @Test
    @DisplayName("발행 일자가 null 일 경우 에러를 발생한다.")
    void issuePeriod_WhenDateIsNull() {
        assertThatThrownBy(() -> new IssuePeriod(null, LocalDate.now()))
                .isInstanceOf(CouponException.class)
                .hasMessage(ErrorConstant.COUPON_ISSUE_DATE_IS_NULL.getMessage());
    }

    @Test
    @DisplayName("종료 일자가 null 일 경우 에러를 발생한다.")
    void issuePeriod_WhenEndDateIsNull() {
        assertThatThrownBy(() -> new IssuePeriod(LocalDate.now(), null))
                .isInstanceOf(CouponException.class)
                .hasMessage(ErrorConstant.COUPON_ISSUE_DATE_IS_NULL.getMessage());
    }
}
