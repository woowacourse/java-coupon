package coupon.domain.coupon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    int countByMemberIdAndCouponId(Long memberId, Long couponId);
}
