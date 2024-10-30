package coupon.domain.member_coupon.repository;

import coupon.domain.member_coupon.MemberCoupon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {
    @Query("""
        SELECT mc
        FROM MemberCoupon mc
        JOIN FETCH Coupon c ON mc.coupon = c
        WHERE mc.memberId = :memberId
    """)
    List<MemberCoupon> findByMemberId(long memberId);
}
