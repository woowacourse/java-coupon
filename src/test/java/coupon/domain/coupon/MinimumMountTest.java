package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MinimumMountTest {

    @ParameterizedTest
    @ValueSource(ints = {5000, 5555, 6000, 50000, 100000})
    @DisplayName("유효한 할인 금액을 통해 생성할 수 있다.")
    void validMountCreation(int mount) {
        assertDoesNotThrow(() -> new MinimumMount(mount));
    }

    @Test
    @DisplayName("최소 할인 금액 미만이면 예외로 처리한다.")
    void mountBelowMinimum() {
        int invalidMount = 4000;
        assertThatThrownBy(() -> new MinimumMount(invalidMount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 5000원 이상이어야 합니다.");
    }

    @Test
    @DisplayName("최대 할인 금액을 초과하면 예외로 처리한다")
    void mountAboveMaximum() {
        int invalidMount = 110000;
        assertThatThrownBy(() -> new MinimumMount(invalidMount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 100000원 이하여야 합니다.");
    }
}
