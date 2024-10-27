package coupon;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.params.provider.Arguments.*;

import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class CouponTest {

    @DisplayName("쿠폰의 이름은 최소 1자 이상 30자 이하만 가능하다.")
    @ValueSource(strings = {"1", "123456789012345678901234567890"})
    @ParameterizedTest
    void nameSuccess(String name) {
        assertThatCode(() -> new Coupon(name, 1000, Category.APPLIANCES, 10000))
                .doesNotThrowAnyException();
    }

    @DisplayName("쿠폰의 이름이 비어있거나, 공백이거나, 30자를 초과하면 에러가 발생한다.")
    @ValueSource(strings = {"", " ", "  ", "1234567890123456789012345678901"})
    @ParameterizedTest
    void nameFail(String name) {
        assertThatCode(() -> new Coupon(name, 1000, Category.APPLIANCES, 10000))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("할인 금액은 1000원 이상, 10000원 이하만 가능하다.")
    @ValueSource(ints = {1000, 10000})
    @ParameterizedTest
    void discountAmountSuccess(Integer discountAmount) {
        assertThatCode(() -> new Coupon("test", discountAmount, Category.APPLIANCES, discountAmount * 10))
                .doesNotThrowAnyException();
    }

    @DisplayName("할인 금액이 1000원 미만이거나 10000원을 초과하면 에러가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {999, 10001})
    void discountAmountFail(Integer discountAmount) {
        assertThatCode(() -> new Coupon("test", discountAmount, Category.APPLIANCES, discountAmount * 10))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("할인 금액이 500원 단위가 아니면 에러가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {501, 1001})
    void discountAmountUnitFail(Integer discountAmount) {
        assertThatCode(() -> new Coupon("test", discountAmount, Category.APPLIANCES, discountAmount * 10))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("할인율은 3% 이상 20% 이하만 가능하다.")
    @ParameterizedTest
    @MethodSource("discountPercentSuccessArguments")
    void discountPercentSuccess(Integer discountAmount, Integer minimumOrderPrice) {
        assertThatCode(() -> new Coupon("test", discountAmount, Category.APPLIANCES, minimumOrderPrice))
                .doesNotThrowAnyException();
    }

    static Stream<Arguments> discountPercentSuccessArguments() {
        return Stream.of(
                arguments(1000, 5_000),
                arguments(3000, 100_000)
        );
    }

    @DisplayName("할인율은 3% 이상 20% 이하만 가능하다.")
    @ParameterizedTest
    @MethodSource("discountPercentFailArguments")
    void discountPercentFail(Integer discountAmount, Integer minimumOrderPrice) {
        assertThatCode(() -> new Coupon("test", discountAmount, Category.APPLIANCES, minimumOrderPrice))
                .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> discountPercentFailArguments() {
        return Stream.of(
                arguments(1001, 5_000),
                arguments(3001, 100_000)
        );
    }

    @DisplayName("최소 주문 금액은 5000원 이상, 100000원 이하만 가능하다.")
    @ParameterizedTest
    @MethodSource("minimumOrderAmountSuccessArguments")
    void minimumOrderAmountSuccess(Integer discountAmount, Integer minimumOrderPrice) {
        assertThatCode(() -> new Coupon("test", discountAmount, Category.APPLIANCES, minimumOrderPrice))
                .doesNotThrowAnyException();
    }

    static Stream<Arguments> minimumOrderAmountSuccessArguments() {
        return Stream.of(
                arguments(1_000, 5_000),
                arguments(10_000, 100_000)
        );
    }

    @DisplayName("최소 주문 금액이 5000원 미만이거나 100000원 초과면 에러가 발생한다.")
    @ParameterizedTest
    @MethodSource("minimumOrderAmountFailArguments")
    void minimumOrderAmountFail(Integer discountAmount, Integer minimumOrderPrice) {
        assertThatCode(() -> new Coupon("test", discountAmount, Category.APPLIANCES, minimumOrderPrice))
                .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> minimumOrderAmountFailArguments() {
        return Stream.of(
                arguments(1_000, 4_999),
                arguments(10_000, 100_001)
        );
    }

    @DisplayName("쿠폰의 발급 기간 시작일은 종료일보다 이전 날짜만 가능하다.")
    @Test
    void issueDateTimeSuccess() {
        assertThatCode(() -> new Coupon("test", 1000, Category.APPLIANCES, 10000, LocalDate.now(), LocalDate.now().plusDays(1)))
                .doesNotThrowAnyException();
    }

    @DisplayName("쿠폰의 발급 기간 시작일이 종료일보다 이후면 에러가 발생한다.")
    @Test
    void issueDateTimeFail() {
        assertThatCode(() -> new Coupon("test", 1000, Category.APPLIANCES, 10000, LocalDate.now(), LocalDate.now().minusDays(1)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
