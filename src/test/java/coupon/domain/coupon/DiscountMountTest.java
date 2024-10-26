package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountMountTest {

    @ParameterizedTest
    @ValueSource(ints = {1000, 1500, 2000, 5000, 10000})
    @DisplayName("유효한 할인 금액을 통해 생성할 수 있다.")
    void validMountCreation(int mount) {
        assertDoesNotThrow(() -> new DiscountMount(mount));
    }

    @Test
    @DisplayName("최소 할인 금액 미만이면 예외로 처리한다.")
    void mountBelowMinimum() {
        int invalidMount = 500;
        assertThatThrownBy(() -> new DiscountMount(invalidMount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 1000원 이상이어야 합니다.");
    }

    @Test
    @DisplayName("최대 할인 금액을 초과하면 예외로 처리한다")
    void mountAboveMaximum() {
        int invalidMount = 15000;
        assertThatThrownBy(() -> new DiscountMount(invalidMount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 10000원 이하여야 합니다.");
    }

    @Test
    @DisplayName("할인 금액이 500원 단위가 아닐 경우 예외로 처리한다")
    void mountNotMultipleOfUnit() {
        int invalidMount = 1250;
        assertThatThrownBy(() -> new DiscountMount(invalidMount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 500원 단위여야 합니다.");
    }
}
