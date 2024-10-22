package coupon.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    List<MemberCoupon> findByCouponIdAndMemberId(Long couponId, Long memberId);
}
