package coupon.repository.membercoupon;

import coupon.entity.membercoupon.MemberCoupon;
import jakarta.persistence.LockModeType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT mc FROM MemberCoupon mc WHERE mc.memberId = :memberId AND mc.couponId = :couponId")
    List<MemberCoupon> findByMemberIdAndCouponIdWithLock(@Param("memberId") Long memberId,
                                                         @Param("couponId") Long couponId);

    int countByMemberIdAndCouponId(Long memberId, Long couponId);

    List<MemberCoupon> findByMemberId(Long memberId);
}
