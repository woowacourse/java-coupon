package coupon.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.exception.ErrorMessage;
import coupon.exception.GlobalCustomException;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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

    @ParameterizedTest
    @MethodSource("issueDateTimes")
    @DisplayName("쿠폰 발급 가능 기간을 확인한다.")
    void isInIssuePeriod(LocalDateTime issueDateTime, boolean expected) {
        // given
        LocalDateTime issueStartedAt = LocalDateTime.now();
        LocalDateTime issueEndedAt = LocalDateTime.now().plusDays(1l);
        IssuePeriod issuePeriod = new IssuePeriod(issueStartedAt, issueEndedAt);

        // when
        boolean actual = issuePeriod.includes(issueDateTime);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> issueDateTimes() {
        return Stream.of(
                Arguments.of(LocalDateTime.now(), true),
                Arguments.of(LocalDateTime.now().plusDays(1), true),
                Arguments.of(LocalDateTime.now().minusDays(1), false),
                Arguments.of(LocalDateTime.now().plusDays(2), false)
        );
    }
}
