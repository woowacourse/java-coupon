package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CouponTest {

    @ParameterizedTest
    @ValueSource(strings = {"", "0123465789012345678901234567891"})
    void 쿠폰의_이름은_최대_30글자이다(String name) {
        assertThatThrownBy(() -> new Coupon(name, 1000L, 30000L, Category.FOOD))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"12", "12345", "이것은_쿠폰"})
    void 쿠폰의_이름이_30자_이내인_경우_예외가_발생하지_않는다(String name) {
        assertThatNoException()
                .isThrownBy(() -> new Coupon(name, 1000L, 30000L, Category.FOOD));
    }
}
