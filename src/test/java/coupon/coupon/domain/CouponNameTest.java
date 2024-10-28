package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CouponNameTest {

    @ParameterizedTest
    @ValueSource(strings = {"새해쿠폰", "쿠폰", "쿠"})
    void 쿠폰_이름이_정상인_경우(String name) {
        assertThatCode(() -> new CouponName(name))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 쿠폰_이름이_비어있는_경우_예외를_발생한다(String name) {
        assertThatThrownBy(() -> new CouponName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름은 필수입니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {31, 100})
    void 쿠폰_이름이_제한을_넘는_경우_예외를_발생한다(int length) {
        final var name = "a".repeat(length);
        assertThatThrownBy(() -> new CouponName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름은 30자를 넘을 수 없습니다.");
    }
}
