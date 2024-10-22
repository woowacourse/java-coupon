package coupon.repository;

import coupon.domain.MemberCoupon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    List<MemberCoupon> findAllByMemberIdAndCouponId(Long memberId, Long couponId);

    List<MemberCoupon> findAllByMemberId(Long memberId);
}
