package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import coupon.coupon.UserCouponResponse;
import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.UserCoupon;
import coupon.coupon.repository.UserCouponRepository;
import coupon.user.domain.User;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserCouponServiceTest {

    private UserCouponRepository userCouponRepository;

    private UserCouponService userCouponService;

    private CouponService couponService;

    private User user;

    private Coupon coupon;

    @BeforeEach
    void setUp() {
        userCouponRepository = mock(UserCouponRepository.class);
        couponService = mock(CouponService.class);
        userCouponService = new UserCouponService(userCouponRepository, couponService);
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

    @Test
    void getUserCouponInfo() {
        Coupon coupon1 = new Coupon("test", 1000, 10000, Category.FASHION, LocalDate.now(), LocalDate.now());
        Coupon coupon2 = new Coupon("test2", 1000, 10000, Category.FASHION, LocalDate.now(), LocalDate.now());
        UserCoupon userCoupon1 = new UserCoupon(1L, user, false, LocalDate.now());
        UserCoupon userCoupon2 = new UserCoupon(2L, user, false, LocalDate.now());

        List<UserCoupon> userCoupons = List.of(userCoupon1, userCoupon2);
        when(userCouponRepository.findByUser(user)).thenReturn(userCoupons);
        when(couponService.getCoupon(1L)).thenReturn(coupon1);
        when(couponService.getCoupon(2L)).thenReturn(coupon2);

        List<UserCouponResponse> responses = userCouponService.getUserCouponInfo(user);
        UserCouponResponse actual1 = UserCouponResponse.of(userCoupon1, coupon1);
        UserCouponResponse actual2 = UserCouponResponse.of(userCoupon2, coupon2);

        assertThat(responses).contains(actual1, actual2);
    }
}
