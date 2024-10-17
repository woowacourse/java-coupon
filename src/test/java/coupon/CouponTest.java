package coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CouponTest {

    @Test
    @DisplayName("쿠폰 생성 성공")
    void couponCreateSuccess() {
        String name = "name";
        Integer discountAmount = 1000;
        assertThatNoException().isThrownBy(() -> new Coupon(name, discountAmount));
    }

    @ParameterizedTest
    @DisplayName("쿠폰 생성 실패: 쿠폰의 이름은 1자 이상, 30자 이하여야 한다.")
    @ValueSource(strings = {"", " ", "0123456789012345678901234567890"})
    void nameRange(String name) {
        assertThatThrownBy(() -> new Coupon(name, 1000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰의 이름은 1자 이상 30자 이하여야 합니다.");
    }

    @Test
    @DisplayName("쿠폰 생성 실패: 할인 금액은 500원 단위로 설정할 수 있다.")
    void discountAmountUnit() {
        Integer discountAmount = 1234;
        assertThatThrownBy(() -> new Coupon("coupon", discountAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 500원 단위로 설정할 수 있습니다.");
    }

    @ParameterizedTest
    @DisplayName("쿠폰 생성 실패: 할인 금액은 1000원 이상, 10000원 이하여야 한다.")
    @ValueSource(ints = {500, 10500})
    void discountAmountRange(Integer discountAmount) {
        assertThatThrownBy(() -> new Coupon("coupon", discountAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 1_000원 이상, 10_000원 이하여야 합니다.");
    }
}
