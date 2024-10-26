package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class CouponNameTest {

    @NullAndEmptySource
    @ParameterizedTest
    void 이름이_공백이나_null이면_예외가_발생한다(String value) {
        assertThatThrownBy(() -> new CouponName(value)
        ).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이름의_길이가_30을_초과하면_예외가_발생한다() {
        assertThatThrownBy(() -> new CouponName("1234567890123456789012345678901")
        ).isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
