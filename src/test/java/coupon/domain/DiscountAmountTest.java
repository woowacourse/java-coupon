package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountAmountTest {

    @DisplayName("할인 금액이 정상 생성된다.")
    @Test
    void createDiscountAmountSuccessfully() {
        int validAmount = 1000;

        assertThatNoException()
                .isThrownBy(() -> new DiscountAmount(validAmount));
    }

    @DisplayName("할인 금액이 1,000원 미만이면 예외를 발생시킨다.")
    @Test
    void throwsWhenAmountLessThanMin() {
        int amount = 999;

        assertThatThrownBy(() -> new DiscountAmount(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 1000원 이상이어야 합니다.");
    }

    @DisplayName("할인 금액이 10,000원 초과하면 예외를 발생시킨다.")
    @Test
    void throwsWhenAmountGraterThanMax() {
        int amount = 10001;

        assertThatThrownBy(() -> new DiscountAmount(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 10000원 이하여야 합니다.");
    }

    @DisplayName("할인 금액이 500원 단위가 아니면 예외를 발생시킨다.")
    @Test
    void throwsWhenAmountNotMultipleOfUnit() {
        int amount = 1100;

        assertThatThrownBy(() -> new DiscountAmount(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 500원 단위로만 설정할 수 있습니다.");
    }
}
