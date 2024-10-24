package coupon.repository;

import coupon.domain.MemberCoupon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    long countByMemberIdAndCouponId(long memberId, long couponId);

    List<MemberCoupon> findByMemberId(long memberId);
}
