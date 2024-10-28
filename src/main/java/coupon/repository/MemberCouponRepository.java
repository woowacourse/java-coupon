package coupon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import coupon.domain.member.Member;
import coupon.domain.member.MemberCoupon;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    int countByMemberAndCouponId(Member member, long couponId);

    List<MemberCoupon> findAllByMemberId(long id);
}
