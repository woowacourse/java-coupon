package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import coupon.domain.coupon.IssuancePeriod;

class IssuancePeriodTest {

    @DisplayName("발급 기간의 시작일이 종료일보다 앞서지 않으면 발급 기간을 생성할 수 있다.")
    @Test
    void createIssuancePeriod() {
        assertThatCode(() -> new IssuancePeriod(LocalDate.now(), LocalDate.now()))
                .doesNotThrowAnyException();
    }

    @DisplayName("발급 기간의 시작일이 종료일보다 앞서면 예외를 발생시킨다.")
    @Test
    void createIssuancePeriodFail() {
        assertThatThrownBy(() -> new IssuancePeriod(LocalDate.now().plusDays(1), LocalDate.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("시작일과 종료일 사이에 특정 날짜가 포함되는지 판단한다.")
    @ParameterizedTest
    @MethodSource("datesProvider")
    void isInRange(LocalDate start, LocalDate end, LocalDate date, boolean expected) {
        // given
        IssuancePeriod issuancePeriod = new IssuancePeriod(start, end);

        // when
        boolean result = issuancePeriod.isInRange(date);

        // then
        assertThat(result).isEqualTo(expected);
    }

    static Stream<Arguments> datesProvider() {
        return Stream.of(
                Arguments.of(LocalDate.now(), LocalDate.now(), LocalDate.now(), true),
                Arguments.of(LocalDate.now(), LocalDate.now(), LocalDate.now().plusDays(1), false),
                Arguments.of(LocalDate.now(), LocalDate.now(), LocalDate.now().minusDays(1), false)
        );
    }
}
