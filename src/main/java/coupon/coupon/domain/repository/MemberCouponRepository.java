package coupon.coupon.domain.repository;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.MemberCoupon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    List<MemberCoupon> findByMemberId(Long memberId);

    int countByMemberIdAndCoupon(Long memberId, Coupon coupon);
}
