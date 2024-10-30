package coupon.coupon.repository;

import coupon.coupon.domain.MemberCoupon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    List<MemberCoupon> findAllByCouponIdAndMemberId(Long couponId, Long memberId);

    List<MemberCoupon> findAllByMemberId(Long memberId);
}
