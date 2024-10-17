package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.exception.ErrorMessage;
import coupon.exception.GlobalCustomException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class CouponTest {

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "30자가 넘는 유효하지 않은 쿠폰 이름 예시입니다!!!!"})
    @DisplayName("쿠폰 이름은 30자 이하다.")
    void validateName(String invalidName) {
        assertThatThrownBy(
                () -> new Coupon(invalidName, 1000, 5_000, Category.FASHION, LocalDateTime.now(), LocalDateTime.now()))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessage(ErrorMessage.INVALID_COUPON_NAME.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {1_020, 9_877})
    @DisplayName("쿠폰 할인금액은 500원 단위다.")
    void validateDiscountAmountUnit(int invalidDiscountAmount) {
        assertThatThrownBy(
                () -> new Coupon("쿠폰이름", invalidDiscountAmount, 5_000, Category.FASHION, LocalDateTime.now(),
                        LocalDateTime.now()))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessage(ErrorMessage.INVALID_COUPON_DISCOUNT_AMOUNT_UNIT.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {500, 10_500})
    @DisplayName("쿠폰 할인금액은 1,000원 이상 10,000원 이하다.")
    void validateDiscountAmountRange(int invalidDiscountAmount) {
        assertThatThrownBy(
                () -> new Coupon("쿠폰이름", invalidDiscountAmount, 5_000, Category.FASHION, LocalDateTime.now(),
                        LocalDateTime.now()))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessage(ErrorMessage.INVALID_COUPON_DISCOUNT_AMOUNT_RANGE.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {500, 110_000})
    @DisplayName("쿠폰의 최소 주문 금액은 5,000원 이상 100,000원 이하다.")
    void validateMinimumOrderPrice(int invalidMinimumOrderPrice) {
        assertThatThrownBy(
                () -> new Coupon("쿠폰이름", 1_000, invalidMinimumOrderPrice, Category.FASHION, LocalDateTime.now(),
                        LocalDateTime.now()))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessage(ErrorMessage.INVALID_MINIMUM_ORDER_PRICE_RANGE.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "2500, 5000",
            "1000, 100000"
    })
    @DisplayName("쿠폰의 할인율은 3% 이상 20% 이하다.")
    void validateDiscountRate(int discountAmount, int minimumOrderPrice) {
        assertThatThrownBy(
                () -> new Coupon("쿠폰이름", discountAmount, minimumOrderPrice, Category.FASHION, LocalDateTime.now(),
                        LocalDateTime.now()))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessage(ErrorMessage.INVALID_DISCOUNT_RATE_RANGE.getMessage());
    }

    @Test
    @DisplayName("쿠폰 발급 시작일은 종료일 이전이다.")
    void validateIssuePeriod() {
        LocalDateTime issueStartedAt = LocalDateTime.now().plusDays(1);
        LocalDateTime issueEndedAt = LocalDateTime.now();

        assertThatThrownBy(
                () -> new Coupon("쿠폰이름", 1_000, 30_000, Category.FASHION, issueStartedAt,
                        issueEndedAt))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessage(ErrorMessage.INVALID_ISSUE_PERIOD.getMessage());
    }
}
