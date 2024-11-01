package coupon.coupon.repository;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.UserCoupon;
import coupon.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    int countByCouponIdAndUser(Long couponId, User user);

    List<UserCoupon> findByUser(User user);

}
