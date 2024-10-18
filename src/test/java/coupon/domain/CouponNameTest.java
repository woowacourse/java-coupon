package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class CouponNameTest {

    @Test
    void 쿠폰_이름이_30자_초과이면_예외가_발생한다() {
        assertThatThrownBy(() -> new CouponName("쿠폰쿠폰쿠폰쿠폰쿠폰쿠폰쿠폰쿠폰쿠폰쿠폰쿠폰쿠폰쿠폰쿠폰쿠폰쿠"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 쿠폰_이름이_30자_이하이면_객체가_생성된다() {
        assertDoesNotThrow(() -> new CouponName("쿠폰쿠폰쿠폰쿠폰쿠폰쿠폰쿠폰쿠폰쿠폰쿠폰쿠폰쿠폰쿠폰쿠폰쿠폰"));
    }
}
