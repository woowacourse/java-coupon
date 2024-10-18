package coupon.domain;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CouponTest {

    @DisplayName("쿠폰 이름이 존재하지 않는 경우 예외가 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void validateNameIsNullOrEmpty(String value) {
        assertThatThrownBy(() -> new Coupon(value, 1000, 5000, Category.FASHION, LocalDateTime.now(), LocalDateTime.now()
                .plusDays(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름은 반드시 존재해야 합니다.");
    }

    @DisplayName("쿠폰 이름이 30자 초과일 경우 예외가 발생한다")
    @Test
    void validateNameLength() {
        String tooLongName = "a".repeat(31);

        assertThatThrownBy(() -> new Coupon(tooLongName, 1000, 5000, Category.FASHION, LocalDateTime.now(), LocalDateTime.now()
                .plusDays(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름은 최대 30자 이하여야 합니다.");
    }

    @DisplayName("할인 금액이 존재하지 않는 경우 예외가 발생한다")
    @Test
    void validateDiscountIsNull() {
        assertThatThrownBy(() -> new Coupon("쿠폰", null, 5000, Category.FASHION, LocalDateTime.now(), LocalDateTime.now()
                .plusDays(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 반드시 존재해야 합니다.");
    }

    @DisplayName("할인 금액이 1,000원 이상 10,000원 이하가 아닌 경우 예외가 발생한다")
    @Test
    void validateDiscountRange() {
        int discountAmount = 500;

        assertThatThrownBy(() -> new Coupon("쿠폰", discountAmount, 5000, Category.FASHION, LocalDateTime.now(), LocalDateTime.now()
                .plusDays(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 1,000원 이상 10,000원 이하여야 합니다.");
    }

    @DisplayName("할인 금액이 500원 단위가 아닐 때 예외가 발생한다")
    @Test
    void validateDiscountUnit() {
        int discountAmount = 1250;

        assertThatThrownBy(() -> new Coupon("쿠폰", discountAmount, 5000, Category.FASHION, LocalDateTime.now(), LocalDateTime.now()
                .plusDays(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 500원 단위로 설정할 수 있습니다.");
    }

    @DisplayName("할인율이 3% 미만 또는 20% 초과시 예외가 발생한다")
    @Test
    void validateDiscountRateRange() {
        int discountAmount = 8000;
        int minOrderAmount = 10000;

        assertThatThrownBy(() -> new Coupon("쿠폰", discountAmount, minOrderAmount, Category.FASHION, LocalDateTime.now(), LocalDateTime.now()
                .plusDays(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 3% 이상 20% 이하여야 합니다.");
    }

    @DisplayName("최소 주문 금액이 5,000원 이상 100,000원 이하가 아닌 경우 예외가 발생한다")
    @Test
    void validateMinOrderAmount() {
        int invalidMinOrderAmount = 100500;

        assertThatThrownBy(() -> new Coupon("쿠폰", 10000, invalidMinOrderAmount, Category.FASHION, LocalDateTime.now(), LocalDateTime.now()
                .plusDays(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 5,000원 이상 100,000원 이하여야 합니다.");
    }

    @DisplayName("발급기간의 종료일이 시작일보다 빠를 경우 예외가 발생한다")
    @Test
    void validateDates() {
        assertThatThrownBy(() -> new Coupon("쿠폰", 1000, 5000, Category.FASHION, LocalDateTime.now(), LocalDateTime.now()
                .minusDays(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("종료일은 시작일보다 이후여야 합니다.");
    }

    @Test
    @DisplayName("모든 값이 유효한 경우 쿠폰 생성에 성공한다")
    void createCoupon() {
        assertThatCode(() -> new Coupon("유효한 쿠폰", 1000, 5000, Category.FASHION, LocalDateTime.now(), LocalDateTime.now()
                .plusDays(1))).doesNotThrowAnyException();
    }
}
