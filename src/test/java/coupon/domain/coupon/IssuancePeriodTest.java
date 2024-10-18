package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class IssuancePeriodTest {

    @DisplayName("시작일은 종료일보다 이전이어야 한다.")
    @Nested
    class validateStartAtBeforeEndAt {

        private static final LocalDateTime BASE_DATE = LocalDateTime.now();
        @ParameterizedTest
        @ValueSource(ints = {0, 1})
        void validateStartAtBeforeEndAtSuccess(int days) {
            assertThatCode(() -> new IssuancePeriod(BASE_DATE, BASE_DATE.plusDays(days)))
                    .doesNotThrowAnyException();
        }

        @Test
        void validateStartAtBeforeEndAtException() {
            assertThatThrownBy(() -> new IssuancePeriod(BASE_DATE, BASE_DATE.minusDays(1)))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
