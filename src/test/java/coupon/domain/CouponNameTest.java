package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import coupon.exception.InvalidCouponNameException;

class CouponNameTest {

    @Test
    @DisplayName("쿠폰 최대 이름 길이를 초과하면 예외가 발생한다.")
    void throw_exception_over_length() {
        assertThatCode(() -> new CouponName("0123456789012345678901234567890"))
                .isExactlyInstanceOf(InvalidCouponNameException.class);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    @DisplayName("쿠폰 이름이 비어있거나 공백이면 예외가 발생한다.")
    void throw_exception_null_or_blank(final String name) {
        assertThatCode(() -> new CouponName(name))
                .isExactlyInstanceOf(InvalidCouponNameException.class);
    }
}
