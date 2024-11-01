package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.user.domain.User;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class UserCouponTest {

    private static final int EXPIRED_LIMIT = 7;

    @Test
    void userCouponIsNotExpired() {
        UserCoupon userCoupon = new UserCoupon(1L, new User(), false, LocalDate.now());
        assertThat(userCoupon.isExpired()).isFalse();
    }

    @Test
    void userCouponIsExpired() {
        UserCoupon userCoupon = new UserCoupon(1L, new User(), false, LocalDate.now().minusDays(EXPIRED_LIMIT + 1));
        assertThat(userCoupon.isExpired()).isTrue();
    }
}
