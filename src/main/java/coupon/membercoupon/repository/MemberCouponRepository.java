package coupon.membercoupon.repository;

import coupon.membercoupon.domain.MemberCoupon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    int countByCouponIdAndMemberId(Long couponId, Long memberId);

    List<MemberCoupon> findByMemberId(Long memberId);
}
