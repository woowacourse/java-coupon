package coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCouponEntity, Long> {

    long countMemberCouponEntitiesByMemberIdAndCoupon(long memberId, CouponEntity coupon);
}
