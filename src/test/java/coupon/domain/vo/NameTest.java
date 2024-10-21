package coupon.domain.vo;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.exception.ErrorMessage;
import coupon.exception.GlobalCustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class NameTest {

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "30자가 넘는 유효하지 않은 쿠폰 이름 예시입니다!!!!"})
    @DisplayName("쿠폰 이름은 30자 이하다.")
    void validateName(String invalidName) {
        assertThatThrownBy(() -> new Name(invalidName))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessage(ErrorMessage.INVALID_COUPON_NAME.getMessage());
    }
}
