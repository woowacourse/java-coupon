package coupon.domain.vo;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.exception.ErrorMessage;
import coupon.exception.GlobalCustomException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IssuePeriodTest {

    @Test
    @DisplayName("쿠폰 발급 시작일은 종료일 이전이다.")
    void validateIssuePeriod() {
        LocalDateTime issueStartedAt = LocalDateTime.now().plusDays(1);
        LocalDateTime issueEndedAt = LocalDateTime.now();

        assertThatThrownBy(() -> new IssuePeriod(issueStartedAt, issueEndedAt))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessage(ErrorMessage.INVALID_ISSUE_PERIOD.getMessage());
    }
}
