package coupon.coupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.UserCoupon;
import coupon.coupon.repository.UserCouponRepository;
import coupon.user.domain.User;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCouponService {

    public static final int MAX_ISSUE_COUNT = 5;
    private final UserCouponRepository userCouponRepository;

    public void issue(Coupon coupon, User user) {
        if (canIssue(coupon, user)) {
            userCouponRepository.save(new UserCoupon(coupon.getId(), user, false, LocalDate.now()));
        }
        throw new IllegalStateException("Coupon issued over 5");
    }

    private boolean canIssue(Coupon coupon, User user) {
        return userCouponRepository.countByUserAndCoupon(user, coupon) <= MAX_ISSUE_COUNT;
    }


}
