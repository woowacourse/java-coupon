package coupon.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponPeriodTest {

    @DisplayName("쿠폰 발급 기간이 정상 생성된다.")
    @Test
    void createValidityPeriodSuccessfully() {
        LocalDateTime issueStartedAt = LocalDateTime.now();
        LocalDateTime issueEndedAt = issueStartedAt.plusDays(3);

        assertThatNoException()
                .isThrownBy(() -> new CouponPeriod(issueStartedAt, issueEndedAt));
    }

    @DisplayName("시작일이 종료일보다 늦으면 예외를 발생시킨다.")
    @Test
    void throwsWhenStartDateIsAfterEndDate() {
        LocalDateTime issueStartedAt = LocalDateTime.of(2024, 10, 16, 0, 0, 0);
        LocalDateTime issueEndedAt = LocalDateTime.of(2024, 10, 15, 23, 59, 59);

        assertThatThrownBy(() -> new CouponPeriod(issueStartedAt, issueEndedAt))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰의 발급 시작일은 종료일보다 이전이어야 합니다.");
    }

    @DisplayName("시작일과 종료일이 같으면 정상 생성된다.")
    @Test
    void createValidityPeriodWithSameDate() {
        LocalDateTime sameDate = LocalDateTime.of(2024, 10, 17, 3, 0, 0);

        assertThatNoException()
                .isThrownBy(() -> new CouponPeriod(sameDate, sameDate));
    }
}
