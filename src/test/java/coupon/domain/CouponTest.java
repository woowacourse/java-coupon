package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.ExceptionMessage;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CouponTest {

    @DisplayName("이름에 공백을 제외하고 빈 값이 들어오면 예외를 발생시킨다")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void nameIsNotBlank(String name) {
        LocalDateTime today = LocalDateTime.now();
        assertThatThrownBy(() -> new Coupon(
                name,
                1000,
                5000,
                Category.ELECTRONICS,
                today,
                today.plusDays(8)
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format(ExceptionMessage.NAME_LENGTH_EXCEPTION.getMessage(), Coupon.MAX_NAME_LENGTH));
    }

    @DisplayName("이름이 30글자를 초과하면 예외를 발생시킨다")
    @Test
    void nameLengthIsNotOver30() {
        LocalDateTime today = LocalDateTime.now();
        assertThatThrownBy(() -> new Coupon(
                "f".repeat(31),
                1000,
                5000,
                Category.ELECTRONICS,
                today,
                today.plusDays(8)
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format(ExceptionMessage.NAME_LENGTH_EXCEPTION.getMessage(), Coupon.MAX_NAME_LENGTH));
    }

    @DisplayName("할인 금액은 1000원 이상 10000원 이하, 단위는 500원이어야 한다")
    @ParameterizedTest
    @ValueSource(ints = {999, 10001, 1501})
    void invalidDiscountAmount(int discountAmount) {
        LocalDateTime today = LocalDateTime.now();
        assertThatThrownBy(() -> new Coupon(
                "nyangin",
                discountAmount,
                5000,
                Category.ELECTRONICS,
                today,
                today.plusDays(8)
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format(
                        ExceptionMessage.DISCOUNT_AMOUNT_EXCEPTION.getMessage(),
                        Coupon.MIN_DISCOUNT_AMOUNT,
                        Coupon.MAX_DISCOUNT_AMOUNT,
                        Coupon.DISCOUNT_AMOUNT_UNIT
                ));
    }

    @DisplayName("최소 주문 금액은 5000원 이상 100000원 이하여야 한다")
    @ParameterizedTest
    @ValueSource(ints = {4999, 100_001})
    void invalidMinimumOrderAmount(int minimumOrderAmount) {
        LocalDateTime today = LocalDateTime.now();
        assertThatThrownBy(() -> new Coupon(
                "nyangin",
                1000,
                minimumOrderAmount,
                Category.ELECTRONICS,
                today,
                today.plusDays(8)
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format(
                        ExceptionMessage.MINIMUM_ORDER_AMOUNT_EXCEPTION.getMessage(),
                        Coupon.MIN_MINIMUM_ORDER_AMOUNT,
                        Coupon.MAX_MINIMUM_ORDER_AMOUNT
                ));
    }

    @DisplayName("할인률이 3% 미만이면 예외를 발생시킨다")
    @Test
    void discountRateIsNotUnder3() {
        LocalDateTime today = LocalDateTime.now();
        assertThatThrownBy(() -> new Coupon(
                "nyangin",
                2500,
                100000,
                Category.ELECTRONICS,
                today,
                today.plusDays(8)
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format(
                        ExceptionMessage.DISCOUNT_RATE_EXCEPTION.getMessage(),
                        Coupon.MIN_DISCOUNT_RATE,
                        Coupon.MAX_DISCOUNT_RATE
                ));
    }

    @DisplayName("할인률이 20%를 초과하면 예외를 발생시킨다")
    @Test
    void discountRateIsNotOver20() {
        LocalDateTime today = LocalDateTime.now();
        assertThatThrownBy(() -> new Coupon(
                "nyangin",
                1500,
                5000,
                Category.ELECTRONICS,
                today,
                today.plusDays(8)
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format(
                        ExceptionMessage.DISCOUNT_RATE_EXCEPTION.getMessage(),
                        Coupon.MIN_DISCOUNT_RATE,
                        Coupon.MAX_DISCOUNT_RATE
                ));
    }

    @DisplayName("시작 날짜가 종료 날짜보다 뒤에 있으면 예외를 발생시킨다")
    @Test
    void startDateIsNotLaterEndDate() {
        LocalDateTime today = LocalDateTime.now();
        assertThatThrownBy(() -> new Coupon(
                "nyangin",
                1000,
                5000,
                Category.ELECTRONICS,
                today,
                today.minusDays(8)
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.START_DATE_BEFORE_END_DATE_EXCEPTION.getMessage());
    }
}
