package coupon.entity;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.CouponException;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CouponTest {

    private final String validName = "name";
    private final int validAmount = 5000;
    private final int validMinimumOrder = 40000;
    private final LocalDate validStart = LocalDate.of(2020, 1, 1);
    private final LocalDate validEnd = LocalDate.of(2020, 12, 31);

    @DisplayName("쿠폰 정상 생성")
    @Test
    void valid() {
        assertThatCode(() -> new Coupon(validName, validAmount, validMinimumOrder, Category.FOOD, validStart, validEnd))
                .doesNotThrowAnyException();
    }

    @DisplayName("쿠폰의 이름은 30자 이하여야한다.")
    @Test
    void validateName() {
        // given
        String invalidName = "a".repeat(31);

        // when & then
        assertThatThrownBy(() ->
                new Coupon(invalidName, validAmount, validMinimumOrder, Category.FOOD, validStart, validEnd))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("쿠폰 할인 금액은 1000원 이상 10000원 이하여야 한다.")
    @ParameterizedTest
    @ValueSource(ints = {999, 10001})
    void validateAmount(int amount) {
        assertThatThrownBy(() ->
                new Coupon(validName, amount, validMinimumOrder, Category.FOOD, validStart, validEnd))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("쿠폰의 최소 주문 금액은 5000원 이상 100000원 이하여야 한다.")
    @ParameterizedTest
    @ValueSource(ints = {4999, 100001})
    void validateMinimumOrder(int minimumOrder) {
        assertThatThrownBy(() ->
                new Coupon(validName, validAmount, minimumOrder, Category.FOOD, validStart, validEnd))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("쿠폰 할인 금액은 최소 주문 금액의 3% 이상 20% 이하여야 한다.")
    @ParameterizedTest
    @ValueSource(ints = {1199, 8400})
    void validateAmountRate(int amount) {
        assertThatThrownBy(() ->
                new Coupon(validName, amount, validMinimumOrder, Category.FOOD, validStart, validEnd))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("쿠폰의 시작일이 종료일보다 늦으면 예외가 발생한다.")
    @Test
    void validateDate() {
        // given
        LocalDate start = LocalDate.of(2024, 10, 11);
        LocalDate end = LocalDate.of(2024, 10, 10);

        // when & then
        assertThatThrownBy(() ->
                new Coupon(validName, validAmount, validMinimumOrder, Category.FOOD, start, end))
                .isInstanceOf(CouponException.class);
    }
}
