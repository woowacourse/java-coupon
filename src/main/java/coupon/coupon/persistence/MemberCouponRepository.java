package coupon.coupon.persistence;

import coupon.coupon.domain.MemberCoupon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    List<MemberCoupon> findAllByMemberId(long memberId);

    int countByMemberIdAndCouponId(long memberId, long couponId);
}
