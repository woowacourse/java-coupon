package coupon.domain.coupon;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import coupon.exception.CouponNameValidationException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CouponNameTest {

    @ParameterizedTest
    @ValueSource(strings = {"", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
    void 쿠폰명은_1자_이상_30자_이하만_가능하다(String couponName) {
        assertThatThrownBy(() -> new CouponName(couponName))
                .isExactlyInstanceOf(CouponNameValidationException.class);
    }
}
