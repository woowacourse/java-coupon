package coupon.membercoupon.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    long countByMemberIdAndCouponId(Long memberId, Long couponId);

    List<MemberCoupon> findByMemberId(Long memberId);
}
