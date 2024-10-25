package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.exception.CouponException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CouponNameTest {

    @DisplayName("쿠폰 이름을 생성한다.")
    @Test
    void create() {
        assertThatCode(() -> new CouponName("쿠폰이름"))
                .doesNotThrowAnyException();
    }

    @DisplayName("쿠폰 이름은 30글자 이하로 반드시 존재해야한다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"1234567890123456789012345678901"})
    void create_Fail(String name) {
        assertThatThrownBy(() -> new CouponName(name))
                .isInstanceOf(CouponException.class);
    }
}
