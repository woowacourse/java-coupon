package coupon.domain.membercounpon;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    long countMemberCouponByMemberId(Long memberId);
}
