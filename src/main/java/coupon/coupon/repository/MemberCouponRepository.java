package coupon.coupon.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import coupon.coupon.domain.MemberCoupon;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    List<MemberCoupon> findAllByMemberIdAndCouponId(long memberId, long couponId);
}
