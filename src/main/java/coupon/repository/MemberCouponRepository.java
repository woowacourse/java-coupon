package coupon.repository;

import coupon.domain.MemberCoupon;
import coupon.domain.member.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    public List<MemberCoupon> findAllByMember(Member member);

    public List<MemberCoupon> findAllByMemberAndCouponId(Member member, Long couponId);
}
