package coupon.domain.membercoupon;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    Long countByMember_IdAndCoupon_Id(Long memberId, Long couponId);

    List<MemberCoupon> findAllByMemberId(Long memberId);
}
