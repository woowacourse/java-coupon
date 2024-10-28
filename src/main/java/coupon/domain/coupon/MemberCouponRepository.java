package coupon.domain.coupon;

import coupon.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    List<MemberCoupon> findAllByMemberAndCoupon(Member member, Coupon coupon);
}
