package coupon.repository;

import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    int countByMemberAndCouponId(Member member, long couponId);

    List<MemberCoupon> findByMember(Member member);
}
