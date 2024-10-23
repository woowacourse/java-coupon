package coupon.coupon.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    int countByCouponIdAndMemberId(long memberId, long couponId);

    List<MemberCoupon> findAllByMemberId(long memberId);
}
