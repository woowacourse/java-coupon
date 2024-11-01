package coupon.repository;

import coupon.domain.MemberCoupon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {
    int countByCouponIdAndMemberId(long couponId, long memberId);

    List<MemberCoupon> findAllByMemberId(long memberId);
}
