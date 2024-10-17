package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.exception.CouponException;
import coupon.exception.ExceptionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class CouponNameTest {

    @DisplayName("쿠폰 이름은 공백일 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createCouponNameWithNullOrEmpty(String name) {
        assertThatThrownBy(() -> new CouponName(name))
                .isInstanceOf(CouponException.class)
                .hasMessage(ExceptionType.COUPON_NAME_BLANK.getMessage());
    }

    @DisplayName("쿠폰 이름은 30자를 초과할 수 없다.")
    @Test
    void createCouponNameWithExceedLength() {
        String given = "a".repeat(31);

        assertThatThrownBy(() -> new CouponName(given))
                .isInstanceOf(CouponException.class)
                .hasMessage(ExceptionType.COUPON_NAME_LENGTH_EXCEED.getMessage());
    }
}
