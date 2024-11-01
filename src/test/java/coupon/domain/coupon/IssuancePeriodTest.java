package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import coupon.coupon.domain.IssuancePeriod;

class IssuancePeriodTest {

    @DisplayName("유효한 쿠폰 시작일, 종료일을 입력되면 객체를 생성한다.")
    @Test
    void createObject() {
        // Given
        final LocalDateTime startDate = LocalDateTime.now();
        final LocalDateTime endDate = LocalDateTime.now().plusDays(7);

        // When
        final IssuancePeriod issuancePeriod = new IssuancePeriod(startDate, endDate);

        // Then
        assertThat(issuancePeriod).isNotNull();
    }

    @DisplayName("시작일, 종료일로 null이 입력되면 예외를 발생시킨다.")
    @MethodSource("validateNullTestSource")
    @ParameterizedTest
    void validateDateIsNull(final LocalDateTime startDate, final LocalDateTime endDate) {
        // When & Then
        assertThatThrownBy(() -> new IssuancePeriod(startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작일 혹은 종료일은 null 값을 입력할 수 없습니다.");
    }

    private static Stream<Arguments> validateNullTestSource() {
        return Stream.of(
                Arguments.of(null, LocalDateTime.now().plusDays(7)),
                Arguments.of(LocalDateTime.now(), null)
        );
    }

    @DisplayName("입력된 시작일이 종료일보다 과거가 아니면 예외를 발생시킨다.")
    @Test
    void validateEndDateIsBeforeThenStartDate() {
        // Given
        final LocalDateTime startDate = LocalDateTime.now();
        final LocalDateTime endDate = LocalDateTime.now().minusSeconds(1);

        // When & Then
        assertThatThrownBy(() -> new IssuancePeriod(startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("종료일이 시작일보다 이전일 수 없습니다. - " + startDate + ", " + endDate);
    }
}
