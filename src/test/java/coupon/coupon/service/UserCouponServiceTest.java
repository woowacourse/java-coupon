package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import coupon.coupon.domain.Coupon;
import coupon.coupon.repository.UserCouponRepository;
import coupon.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserCouponServiceTest {

    private UserCouponRepository userCouponRepository;

    private UserCouponService userCouponService;

    private User user;

    private Coupon coupon;

    @BeforeEach
    void setUp() {
        userCouponRepository = mock(UserCouponRepository.class);
        userCouponService = new UserCouponService(userCouponRepository);
        user = mock(User.class);
        coupon = mock(Coupon.class);
    }

    @Test
    void overIssueCount() {
        when(userCouponRepository.countByCouponIdAndUser(any(Long.class), any(User.class)))
                .thenReturn(5);

        assertThatThrownBy(() -> userCouponService.issue(coupon, user))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void underIssueCount() {
        when(userCouponRepository.countByCouponIdAndUser(any(Long.class), any(User.class)))
                .thenReturn(4);

        assertDoesNotThrow(() -> userCouponService.issue(coupon, user));
    }
}
