package coupon.repository;

import coupon.domain.Coupon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query("""
            SELECT memberCoupon.coupon
            FROM MemberCoupon memberCoupon
            WHERE memberCoupon.member.id = :memberId
            """)
    List<Coupon> findMine(@Param("memberId") long memberId);
}
