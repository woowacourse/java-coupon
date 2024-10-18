package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CouponNameTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("쿠폰의 값이 null이거나 비어 있을 경우 예외가 발생한다.")
    void invalid_coupon_name(final String value) {
        assertThatThrownBy(() -> new CouponName(value))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이름이 최대 길이를 넘어가면 예외가 발생한다.")
    void over_max_length() {
        //given
        final String value = "a".repeat(31);

        //when && then
        assertThatThrownBy(() -> new CouponName(value))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("정상적인 이름으로 쿠폰 이름을 생성한다.")
    @ValueSource(strings = {"오저치고", "1+1 할인 상품"})
    void valid_name(final String value) {
        //given && when
        final CouponName name = new CouponName(value);

        //then
        assertThat(name.getValue()).isEqualTo(value);
    }
}
