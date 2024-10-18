package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.Test;

class CouponNameTest {

    @Test
    void 이름이_비어있으면_예외를_발생한다() {
        assertThatCode(() -> new CouponName(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이름이_최대_글자를_넘어가면_예외를_발생한다() {
        assertThatCode(() -> new CouponName("값".repeat(31)))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
