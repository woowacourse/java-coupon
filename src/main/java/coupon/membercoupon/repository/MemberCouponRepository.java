package coupon.membercoupon.repository;

import coupon.membercoupon.domain.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    Long countByCouponIdAndMemberId(long couponId, long memberId);

    List<MemberCoupon> findAllByMemberId(long memberId);
}
