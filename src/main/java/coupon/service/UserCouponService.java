package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.UserCoupon;
import coupon.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserCouponService {

    private static final int ISSUEABLE_COUPON_MAX_COUNT = 5;

    private final CouponService couponService;
    private final UserService userService;
    private final UserCouponRepository userCouponRepository;

    @Transactional
    public void createUserCoupon(Long couponId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredDate = now.plusDays(7);

        List<UserCoupon> userCoupons = userService.getUsersByCouponCount(ISSUEABLE_COUPON_MAX_COUNT)
                .stream()
                .map(user -> new UserCoupon(couponId, user, true, now, expiredDate))
                .toList();

        userCouponRepository.saveAll(userCoupons);
    }

    @Transactional(readOnly = true)
    public List<Coupon> getUserCoupons(Long userId) {
        return userCouponRepository.findAllByUserId(userId)
                .stream()
                .map(userCoupon -> couponService.getCoupon(userCoupon.getCouponId()))
                .toList();
    }
}
