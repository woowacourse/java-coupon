package coupon.service;

import coupon.domain.UserCoupon;
import coupon.repository.UserCouponRepository;
import coupon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserCouponService {

    private final UserRepository userRepository;
    private final UserCouponRepository userCouponRepository;

    @Transactional
    public void createUserCoupon(Long couponId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredDate = now.plusDays(7);

        List<UserCoupon> userCoupons = userRepository.findAll().stream()
                .map(user -> new UserCoupon(couponId, user, false, now, expiredDate))
                .toList();

        userCouponRepository.saveAll(userCoupons);
    }
}
