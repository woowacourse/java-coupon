package coupon.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import coupon.domain.MemberCoupon;

public interface MemberCouponJpaRepository extends JpaRepository<MemberCoupon, Long> {

    List<MemberCoupon> findAllByMemberId(long memberId);

    int countByMemberIdAndCouponId(long memberId, long couponId);
}
