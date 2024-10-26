package coupon.domain.membercounpon;

import coupon.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    long countMemberCouponByMember(Member member);
}
