package coupon.domain.membercoupon;

import coupon.domain.coupon.Coupon;
import coupon.domain.member.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {
    List<MemberCoupon> findAllByMemberId(Long memberId);

    Integer countByMemberAndCoupon(Member member, Coupon coupon);
}
