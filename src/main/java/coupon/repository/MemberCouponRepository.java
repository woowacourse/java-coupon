package coupon.repository;

import coupon.domain.coupon.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    int countByMemberIdAndCouponId(Long memberId, Long couponId);
}
