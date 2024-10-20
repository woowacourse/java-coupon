package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IssuePeriodTest {

    private LocalDateTime startAt;

    @BeforeEach
    void initDate() {
        startAt = LocalDateTime.now();
    }

    @DisplayName("쿠폰 발급 시작일이 종료일보다 이후이면 예외가 발생한다.")
    @Test
    void invalidIssuePeriod() {
        LocalDateTime endAt = startAt.minusDays(1L);

        assertThatThrownBy(() -> new IssuePeriod(startAt, endAt))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("쿠폰 발급 시작일이 종료일보다 이전이면 예외가 발생하지 않는다.")
    @Test
    void validIssuePeriod() {
        LocalDateTime endAt = startAt.plusDays(1L);

        assertThatCode(() -> new IssuePeriod(startAt, endAt))
                .doesNotThrowAnyException();
    }
}
