package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.coupon.exception.InvalidCouponNameException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NameTest {

    @Test
    @DisplayName("쿠폰의 이름 반드시 존재해야 한다.")
    void nameNull() {
        assertThatThrownBy(() -> new Name(null))
                .isInstanceOf(InvalidCouponNameException.class)
                .hasMessage("쿠폰 이름은 반드시 존재해야 하고 30자 이하여야 합니다.");
    }

    @Test
    @DisplayName("쿠폰 이름은 반드시 존재해야 하고 30자 이하여야한다.")
    void tooLongName() {
        String tooLongName = IntStream.range(0, 31).mapToObj(i -> "a").collect(Collectors.joining());
        assertThatThrownBy(() -> new Name(tooLongName))
                .isInstanceOf(InvalidCouponNameException.class)
                .hasMessage("쿠폰 이름은 반드시 존재해야 하고 30자 이하여야 합니다.");
    }
}
