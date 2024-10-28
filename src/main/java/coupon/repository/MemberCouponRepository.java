package coupon.repository;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    int countByMemberAndCoupon(Member member, Coupon coupon);

    List<MemberCoupon> findByMember(Member member);
}
