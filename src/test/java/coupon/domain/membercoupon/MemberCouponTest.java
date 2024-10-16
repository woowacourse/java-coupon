package coupon.domain.membercoupon;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MemberCouponTest {

    private static Stream<Arguments> isUsableParameters() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2024, 10, 31, 23, 59, 59, 999999999), false),
                Arguments.of(LocalDateTime.of(2024, 11, 1, 0, 0, 0, 0), true),
                Arguments.of(LocalDateTime.of(2024, 11, 3, 23, 59, 59, 999999999), true),
                Arguments.of(LocalDateTime.of(2024, 11, 4, 0, 0, 0, 0), false)
        );
    }

    @ParameterizedTest
    @MethodSource("isUsableParameters")
    @DisplayName("사용하지 않은 쿠폰의 시간별 사용 가능 여부를 확인한다.")
    void isUsable(LocalDateTime base, boolean expected) {
        LocalDate startDate = LocalDate.of(2024, 11, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 3);

        MemberCoupon memberCoupon = new MemberCoupon(1L, 1L, 1L, false, startDate, endDate);

        boolean actual = memberCoupon.isUsable(base);

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("isUsableParameters")
    @DisplayName("사용한 쿠폰의 시간별 사용 가능 여부를 확인한다.")
    void isUsableAlreadyUse(LocalDateTime base, boolean ignore) {
        LocalDate startDate = LocalDate.of(2024, 11, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 3);

        MemberCoupon memberCoupon = new MemberCoupon(1L, 1L, 1L, true, startDate, endDate);

        boolean actual = memberCoupon.isUsable(base);

        assertThat(actual).isFalse();
    }
}
