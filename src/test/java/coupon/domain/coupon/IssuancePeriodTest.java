package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IssuancePeriodTest {

    @Test
    @DisplayName("시작일은 종료일보다 이전이어야 한다.")
    void issuancePeriod() {
        LocalDateTime startDate = LocalDateTime.now().plusDays(1);
        LocalDateTime endDate = LocalDateTime.now();

        assertThatThrownBy(() -> new IssuancePeriod(startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
