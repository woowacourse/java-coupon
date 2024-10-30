package coupon.repository;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    List<MemberCoupon> findAllByMemberAndCoupon(Member member, Coupon coupon);

    List<MemberCoupon> findAllByMember(Member member);
}
