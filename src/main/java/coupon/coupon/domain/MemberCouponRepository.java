package coupon.coupon.domain;

import coupon.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    int countByMemberAndCoupon(Member member, Coupon coupon);
}
