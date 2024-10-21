package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class CouponNameTest {

    @Test
    void 쿠폰명_객체를_생성한다() {
        // given
        String name = "우테코치킨 첫주문 할인";

        // when
        CouponName couponName = new CouponName(name);

        // then
        assertThat(couponName.getName()).isEqualTo(name);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 쿠폰명이_비어있으면_IllegalArgumentException이_발생한다(String name) {
        // when & then
        assertThatThrownBy(() -> new CouponName(name))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰명이 비어있습니다.");
    }

    @Test
    void 쿠폰명이_30자를_넘으면_IllegalArgumentException이_발생한다() {
        // given
        int invalidNameLength = 31;
        String name = "e".repeat(invalidNameLength);

        // when & then
        assertThatThrownBy(() -> new CouponName(name))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰명은 30자 이하여야 합니다.");
    }
}
