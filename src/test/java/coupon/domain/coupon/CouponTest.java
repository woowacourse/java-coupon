package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CouponTest {

    @DisplayName("쿠폰 이름이 유효하면 예외가 발생하지 않는다.")
    @ValueSource(strings = {"coupon", "Coupon"})
    @ParameterizedTest
    void couponWithValidName(String couponName) {
        // when & then
        assertThatCode(
                () -> new Coupon(
                        null, couponName,
                        1000, 10000, Category.FOOD,
                        LocalDateTime.now(), LocalDateTime.now().plusDays(30)
                )
        ).doesNotThrowAnyException();

    }

    @DisplayName("쿠폰 이름이 비어 있으면 예외가 발생한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void validateNameNotBlank(String couponName) {
        // when & then
        assertThatThrownBy(
                () -> new Coupon(
                        null, couponName,
                        1000, 10000, Category.FOOD,
                        LocalDateTime.now(), LocalDateTime.now().plusDays(30)
                )
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("쿠폰 이름이 30자를 초과하면 예외가 발생한다.")
    @Test
    void validateNameLength() {
        // given
        String testName = "name".repeat(10);

        // when & then
        assertThatThrownBy(
                () -> new Coupon(
                        null, testName,
                        1000, 10000, Category.FOOD,
                        LocalDateTime.now(), LocalDateTime.now().plusDays(30)
                )
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("할인 금액은 1000원 이상, 10000원 이하여야 한다.")
    @ValueSource(ints = {999, 1001, 9999, 10001})
    @ParameterizedTest
    void validateDiscountAmount(int discountAmount) {
        // when & then
        assertThatThrownBy(
                () -> new Coupon(
                        null, "couponName",
                        discountAmount, 10000, Category.FOOD,
                        LocalDateTime.now(), LocalDateTime.now().plusDays(30)
                )
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("할인율은 3% 이상, 20% 이하여야 한다.")
    @ValueSource(ints = {2, 21})
    @ParameterizedTest
    void validateDiscountRate(int discountRate) {
        // givien
        int discountAmount = 1000;
        int minimumOrderAmount = (int) ((double) discountAmount / discountRate * 100);

        // when & then
        assertThatThrownBy(
                () -> new Coupon(
                        null, "couponName",
                        1000, minimumOrderAmount, Category.FOOD,
                        LocalDateTime.now(), LocalDateTime.now().plusDays(30)
                )
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("최소 주문 금액은 5000원 이상, 100000원 이하여야 한다.")
    @ValueSource(ints = {4999, 100001})
    @ParameterizedTest
    void validateMinimumOrderAmount(int minimumOrderAmount) {
        // when & then
        assertThatThrownBy(
                () -> new Coupon(
                        null, "couponName",
                        1000, minimumOrderAmount, Category.FOOD,
                        LocalDateTime.now(), LocalDateTime.now().plusDays(30)
                )
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("쿠폰의 시작일이 종료일보다 늦으면 예외가 발생한다.")
    @Test
    void validateDate() {
        // when & then
        assertThatThrownBy(
                () -> new Coupon(
                        null, "couponName",
                        1000, 10000, Category.FOOD,
                        LocalDateTime.now().plusSeconds(1L), LocalDateTime.now()
                )
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
