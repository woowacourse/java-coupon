package coupon.coupon.domain.repository;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    int countByMemberIdAndCoupon(Long memberId, Coupon coupon);
}
