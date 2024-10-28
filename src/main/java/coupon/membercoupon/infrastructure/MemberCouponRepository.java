package coupon.membercoupon.infrastructure;

import coupon.membercoupon.domain.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    List<MemberCoupon> findByCouponIdAndMemberId(long couponId, long memberId);
}
