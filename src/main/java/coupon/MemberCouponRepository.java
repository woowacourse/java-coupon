package coupon;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;import org.springframework.stereotype.Repository;

@Repository
public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    Long countMemberCouponsByCouponIdAndMemberId(Long couponId, Long memberId);

    @Query("select mc from MemberCoupon mc where mc.couponId = :couponId and mc.memberId = :memberId")
    List<MemberCoupon> findAllByCouponAndMember(Long couponId, Long memberId);
}
