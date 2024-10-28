package coupon.repository;

import coupon.domain.membercoupon.MemberCoupon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    int countByCouponIdAndMemberId(Long couponId, Long memberId);

    List<MemberCoupon> findAllByMemberId(long memberId);
}
