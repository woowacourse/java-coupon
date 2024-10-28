package coupon.repository;

import coupon.domain.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    int countByCouponIdAndMemberId(Long couponId, Long memberId);
}
