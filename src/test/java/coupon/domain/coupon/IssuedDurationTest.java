package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class IssuedDurationTest {

    @Test
    void 예외_발급시작일이_null일때() {
        // given
        LocalDateTime issueStartedAt = null;
        LocalDateTime issueEndedAt = LocalDateTime.now();

        // when & then
        assertThatThrownBy(() -> new IssuedDuration(issueStartedAt, issueEndedAt))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("발급 시작일은 null일 수 없습니다.");
    }

    @Test
    void 예외_발급종료일이_null일때() {
        // given
        LocalDateTime issueStartedAt = LocalDateTime.now();
        LocalDateTime issueEndedAt = null;

        // when & then
        assertThatThrownBy(() -> new IssuedDuration(issueStartedAt, issueEndedAt))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("발급 종료일은 null일 수 없습니다.");
    }

    @Test
    void 예외_시작일이_종료일보다_이후일때() {
        // given
        LocalDateTime issueStartedAt = LocalDateTime.now().plusHours(1);
        LocalDateTime issueEndedAt = LocalDateTime.now();

        // when & then
        assertThatThrownBy(() -> new IssuedDuration(issueStartedAt, issueEndedAt))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작일은 종료일보다 이후일 수 없습니다: " + issueStartedAt + " " + issueEndedAt);
    }
}
