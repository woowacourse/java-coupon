package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.coupon.CouponName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CouponNameTest {

    private static final int COUPON_NMAE_MAX_LENGTH = 30;

    @DisplayName("쿠폰 이름의 최대 길이를 초과할 수 없다")
    @Test
    void throwIllegalArgumentException_When_LongerThanMaxLength() {
        String invalidName = "*".repeat(COUPON_NMAE_MAX_LENGTH + 1);
        assertThatThrownBy(() -> new CouponName(invalidName))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("쿠폰 이름은 빈칸과 널, 공백일 수 없다")
    @NullAndEmptySource
    @ValueSource(strings = "   ")
    @ParameterizedTest
    void throwIllegalArgumentException_When_NullOrEmpty(String invalidName) {
        assertThatThrownBy(() -> new CouponName(invalidName))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이름을 생성할 수 있다")
    @Test
    void createCouponName() {
        String validName = "*".repeat(30);

        assertThatCode(() -> new CouponName(validName))
                .doesNotThrowAnyException();
    }
}
