package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class CouponTest {

    @Test
    void 할인율이_3퍼센트_미만이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Coupon(
                "쿠폰",
                Category.FASHION,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                1000000,
                10000)
        ).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 할인율이_20퍼센트를_초과하면_에외가_발생한다() {
        assertThatThrownBy(() -> new Coupon(
                "쿠폰",
                Category.FASHION,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                10000,
                9000)
        ).isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
