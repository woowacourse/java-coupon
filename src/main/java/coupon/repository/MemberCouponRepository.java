package coupon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import coupon.domain.member.MemberCoupon;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    int countByMemberIdAndCouponId(long memberId, long couponId);

    List<MemberCoupon> findAllByMemberId(long id);
}
