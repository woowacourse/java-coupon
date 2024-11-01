package coupon.repository;

import coupon.domain.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    long countByMemberIdAndCouponId(Long memberId, Long couponId);

    List<MemberCoupon> findByMemberId(Long memberId);
}
