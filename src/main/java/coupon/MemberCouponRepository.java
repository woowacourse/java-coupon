package coupon;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

     Long countMemberCouponsByCouponIdAndMemberId(Long couponId, Long memberId);

    List<MemberCoupon> findAllByMemberId(Long memberId);
}
