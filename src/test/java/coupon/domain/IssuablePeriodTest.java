package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class IssuablePeriodTest {

    @Test
    void 시작일이_종료일보다_이후이면_예외가_발생한다() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.minusHours(1);

        assertThatThrownBy(() -> new IssuablePeriod(startTime, endTime))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
