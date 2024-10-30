package coupon.repository;

import coupon.domain.MemberCoupon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    @Query("SELECT mc FROM MemberCoupon mc JOIN Coupon c ON mc.couponId = c.id WHERE mc.memberId = :memberId")
    List<MemberCoupon> findAllByMemberId(Long memberId);

    int countByMemberId(Long memberId);
}
