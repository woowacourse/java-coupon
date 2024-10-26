package coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CouponTest {

    @ParameterizedTest
    @DisplayName("이름이 비었거나 30자가 넘는 경우 예외를 발생한다.")
    @NullAndEmptySource
    @ValueSource(strings = {"1212121212121212121212121212121212121212"})
    void validateNameTest(String testName) {
        assertThatThrownBy(() -> new Coupon(testName, new Discount(1000, 10000), Category.FASHION, new CouponIssuanceDate(LocalDateTime.now(), LocalDateTime.now())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Name cannot be null or more than 30");
    }
}
