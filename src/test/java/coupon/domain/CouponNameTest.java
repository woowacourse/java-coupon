package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class CouponNameTest {

    @DisplayName("쿠폰 이름이 정상 생성된다.")
    @Test
    void createCouponNameSuccessfully() {
        String name = "jazz coupon";

        assertThatNoException()
                .isThrownBy(() -> new CouponName(name));
    }

    @DisplayName("쿠폰 이름이 존재하지 않으면 예외를 발생시킨다.")
    @ParameterizedTest
    @NullAndEmptySource
    void throwsWhenNullAndEmpty(String name) {
        assertThatThrownBy(() -> new CouponName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름은 반드시 존재해야 합니다.");
    }

    @DisplayName("쿠폰 이름이 30글자를 넘으면 예외를 발생시킨다.")
    @Test
    void throwsWhenTooLong() {
        String name = "j".repeat(31);

        assertThatThrownBy(() -> new CouponName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름 길이는 최대 30자 이하여야 합니다.");
    }
}
