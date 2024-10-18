package coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CouponTest {

    @ParameterizedTest
    @DisplayName("이름이 비었거나 30자가 넘는 경우 예외를 발생한다.")
    @NullAndEmptySource
    @ValueSource(strings = {"1212121212121212121212121212121212121212"})
    void validateNameTest(String testName) {
        assertThatThrownBy(() -> new Coupon(testName, 1000, 10000, Category.FASHION, LocalDateTime.now(), LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Name cannot be null or more than 30");
    }

    @Nested
    @DisplayName("할인 금액 관련 검증")
    class validateDiscount {

        @ParameterizedTest
        @DisplayName("할인 금액이 1,000원 미만이거나 10,000원 초과인 경우 예외를 발생한다.")
        @ValueSource(ints = {999, 10001})
        void validateDiscountAmountTest(int testDiscountAmount) {
            assertThatThrownBy(() -> new Coupon("testCoupon", testDiscountAmount, 10000, Category.FASHION, LocalDateTime.now(), LocalDateTime.now()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Discount Amount cannot be less than 1,000 or more than 10,000");
        }

        @Test
        @DisplayName("할인 금액이 500원의 배수가 아닌 경우 예외를 발생한다.")
        void validateDiscountMultipleTest() {
            int testDiscountAmount = 1501;
            assertThatThrownBy(() -> new Coupon("testCoupon", testDiscountAmount, 10000, Category.FASHION, LocalDateTime.now(), LocalDateTime.now()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Discount Amount must be multiple of 500");
        }

        @ParameterizedTest
        @DisplayName("최소 주문 금액이 5,000원 미만이거나 100,000원 초과인 경우 예외를 발생한다.")
        @ValueSource(ints = {4999, 100001})
        void validateMinOrderAmountTest(int testMinOrderAmount) {
            assertThatThrownBy(() -> new Coupon("testCoupon", 1000, testMinOrderAmount, Category.FASHION, LocalDateTime.now(), LocalDateTime.now()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Minimum Order Amount cannot be less than 5,000 or more than 100,000");
        }

        @ParameterizedTest
        @DisplayName("할인률이 3% 미만이거나 20% 초과인 경우 예외를 발생한다.")
        @CsvSource(value = {"1000, 35000", "10000, 45000"})
        void validateDiscountRateTest(int testDiscountAmount, int testMinOrderAmount) {
            assertThatThrownBy(() -> new Coupon("testCoupon", testDiscountAmount, testMinOrderAmount, Category.FASHION, LocalDateTime.now(), LocalDateTime.now()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Discount Rate cannot be less than 3% or more than 20%");
        }
    }

    @Test
    @DisplayName("발급 시작일이 발급 종료일보다 늦은 경우 예외를 발생한다.")
    void validateIssuanceDateTest() {
        assertThatThrownBy(() -> new Coupon("testCoupon", 1000, 10000, Category.FASHION, LocalDateTime.now().plusDays(1), LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Issuance End Date cannot be earlier than Issuance Start Date");
    }
}
