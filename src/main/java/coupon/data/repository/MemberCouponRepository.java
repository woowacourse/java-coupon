package coupon.data.repository;

import coupon.data.MemberCoupon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon,Long> {
    List<MemberCoupon> findAllByMemberIdAndCouponId(long memberId, long couponId);

    List<MemberCoupon> findAllByMemberId(long memberId);
}
