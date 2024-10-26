package coupon.domain.coupon;


import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class CouponDurationTest {

    @Test
    void 종료날짜가_시작날짜보다_이전이면_예외가_발생한다() {
        assertThatThrownBy(
                () -> new CouponDuration(LocalDateTime.now(), LocalDateTime.now().minusMinutes(1))
        ).isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
