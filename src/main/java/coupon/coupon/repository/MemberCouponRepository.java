package coupon.coupon.repository;

import coupon.coupon.domain.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    int countAllByMemberIdAndCouponId(long memberId, long couponId);
}
