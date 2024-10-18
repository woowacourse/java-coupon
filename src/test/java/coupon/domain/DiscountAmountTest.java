package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import coupon.domain.coupon.DiscountAmount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountAmountTest {

    @ParameterizedTest
    @ValueSource(longs = {999, 10001})
    @DisplayName("정해진 할인 금액 범위를 벗어나면 예외 발생")
    void givenDiscountAmountIsExceedRange(long discountAmount) {
        assertThatCode(() -> new DiscountAmount(discountAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("정해진 할인 금액 범위를 벗어났습니다.");
    }

    @Test
    @DisplayName("할인 금액이 500원 단위가 아니면 예외 발생")
    void givenDiscountAmountIsNotMultipleOf500() {
        assertThatCode(() -> new DiscountAmount(5001))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("할인 금액은 정해진 단위로 입력해주세요.");
    }
}
