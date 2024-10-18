package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountAmountTest {

    @DisplayName("할인 금액이 1,000원 이상 10,000원 이하이고 500원 단위로 설정되어 있다면, 생성에 성공한다.")
    @ParameterizedTest
    @ValueSource(ints = {1000, 10000})
    void createDiscountAmount(int amount) {
        assertThatCode(() -> new DiscountAmount(amount)).doesNotThrowAnyException();
    }

    @DisplayName("할인 금액이 1,000원 이하 10,000원 초과면 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(ints = {500, 10500})
    void createDiscountFailByRange(int amount) {
        assertThatThrownBy(() -> new DiscountAmount(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("할인 금액은 1,000원 이상 10,000원 이하여야 합니다.");
    }

    @DisplayName("할인 금액이 500원 단위로 설정되어있지 않다면 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(ints = {1001, 9999})
    void createDiscountFailByUnit(int amount) {
        assertThatThrownBy(() -> new DiscountAmount(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("할인 금액은 500원 단위로 설정할 수 있습니다.");
    }
}
