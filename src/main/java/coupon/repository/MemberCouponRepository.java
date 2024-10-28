package coupon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCouponEntity, Long> {

    List<MemberCouponEntity> findAllByMemberId(long memberId);

    long countMemberCouponEntitiesByMemberIdAndCoupon(long memberId, CouponEntity coupon);
}
