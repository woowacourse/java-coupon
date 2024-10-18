package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IssuablePeriodTest {

    @Test
    @DisplayName("발급 시작일이 발급 종료일보다 이후면 예외가 발생한다.")
    void should_throw_exception_when_issue_startAt_over_than_issue_endAt() {
        // given
        LocalDate startAt = LocalDate.parse("2024-11-29");
        LocalDate endAt = LocalDate.parse("2024-02-16");

        // when & then
        assertThatThrownBy(() -> new IssuablePeriod(startAt, endAt))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작일은 종료일과 같거나 이전이어야 합니다.");
    }

    @Test
    @DisplayName("발급 요청한 시간이 발급 가능한 시간이면 참을 반환한다.")
    void should_return_true_when_request_time_is_issuable_time() {
        // given
        LocalDate issuableDate = LocalDate.parse("2024-10-18");
        IssuablePeriod issuablePeriod = new IssuablePeriod(issuableDate, issuableDate);
        LocalDateTime requestTime = LocalDateTime.parse("2024-10-18T13:00:00");

        // when
        boolean actual = issuablePeriod.canIssue(requestTime);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("발급 요청한 시간이 발급 가능한 시간이 아니면 거짓을 반환한다.")
    void should_return_false_when_request_time_is_issuable_time() {
        // given
        LocalDate issuableDate = LocalDate.parse("2024-10-18");
        IssuablePeriod issuablePeriod = new IssuablePeriod(issuableDate, issuableDate);
        LocalDateTime requestTime = LocalDateTime.parse("2024-10-18T23:59:59.999999001");

        // when
        boolean actual = issuablePeriod.canIssue(requestTime);

        // then
        assertThat(actual).isFalse();
    }
}
