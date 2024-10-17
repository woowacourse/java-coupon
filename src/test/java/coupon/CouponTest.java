package coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class CouponTest {

    @ParameterizedTest
    @DisplayName("쿠폰의 이름은 1자 이상 30자 이하여야 한다.")
    @MethodSource("provideInvalidNames")
    void couponHasName(String name) {
        assertThatThrownBy(() -> new Coupon(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰의 이름은 1자 이상 30자 이하여야 합니다.");
    }

    private static Stream<String> provideInvalidNames() {
        return Stream.of(null, "", " ", "0123456789012345678901234567890");
    }
}
