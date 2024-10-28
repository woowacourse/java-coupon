package coupon.repository.membercoupon;

import coupon.entity.membercoupon.MemberCoupon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {
    int countByMemberIdAndCouponId(Long memberId, Long couponId);

    List<MemberCoupon> findByMemberId(Long memberId);
}
