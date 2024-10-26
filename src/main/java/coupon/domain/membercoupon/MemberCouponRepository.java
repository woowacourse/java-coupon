package coupon.domain.membercoupon;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    List<MemberCoupon> findByMemberIdAndCouponId(Long memberId, Long couponId);
}
