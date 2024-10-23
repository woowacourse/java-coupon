package coupon.repository;

import coupon.domain.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    int countByMemberIdAndCouponId(long memberId, long couponId);
}
