package coupon.membercoupon.domain;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.coupon.domain.IssuedPeriod;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    @DisplayName("이미 사용한 쿠폰은 쿠폰 사용가능한 기간에도 사용할 수 없다")
    @Test
    void isAvailable() {
        // given
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusDays(7);
        AvailablePeriod availablePeriod = new AvailablePeriod(new IssuedPeriod(start, end));
        MemberCoupon memberCoupon = new MemberCoupon(false, availablePeriod);
        boolean expected = false;

        // when
        boolean actual = memberCoupon.isUseable();

        // then
        assertThat(actual).isEqualTo(expected);
    }

}
