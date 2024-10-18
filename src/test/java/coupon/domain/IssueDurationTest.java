package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.coupon.IssueDuration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IssueDurationTest {

    @DisplayName("시작일자가 종료일자보다 이후일 수 없다")
    @Test
    void throwIllegalArgumentException_When_StartTimeIsAfterThanEndTime() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.minusSeconds(1L);

        assertThatThrownBy(() -> new IssueDuration(startTime, endTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("쿠폰 만료기간을 설정할 수 있다")
    @Test
    void createIssueDuration() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusSeconds(1L);

        assertThatCode(() -> new IssueDuration(startTime, endTime))
                .doesNotThrowAnyException();
    }


}
