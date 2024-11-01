package coupon.coupon.service;

import coupon.coupon.UserCouponResponse;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.UserCoupon;
import coupon.coupon.repository.UserCouponRepository;
import coupon.user.domain.User;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCouponService {

    private static final int MAX_ISSUE_COUNT = 5;

    private final UserCouponRepository userCouponRepository;

    private final CouponService couponService;

    public void issue(Coupon coupon, User user) {
        if (canIssue(coupon, user)) {
            userCouponRepository.save(new UserCoupon(coupon.getId(), user, false, LocalDate.now()));
            return;
        }
        throw new IllegalStateException("Coupon issued over 5");
    }

    private boolean canIssue(Coupon coupon, User user) {
        return userCouponRepository.countByCouponIdAndUser(coupon.getId(), user) < MAX_ISSUE_COUNT;
    }

    @Transactional(readOnly = true)
    public List<UserCouponResponse> getUserCouponInfo(User user) {
        List<UserCoupon> userCoupons = userCouponRepository.findByUser(user);
        return userCoupons.stream()
                .map(
                        userCoupon -> UserCouponResponse.of(
                                userCoupon,
                                couponService.getCoupon(userCoupon.getCouponId())
                        )
                )
                .toList();
    }
}
