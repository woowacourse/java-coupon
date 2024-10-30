package member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import member.domain.MemberCoupon;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

	List<MemberCoupon> findByCouponIdAndMemberId(long couponId, long memberId);
	List<MemberCoupon> findByMemberId(long memberId);
}
