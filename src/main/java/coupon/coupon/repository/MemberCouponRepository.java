package coupon.coupon.repository;

import coupon.coupon.domain.MemberCoupon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    int countAllByMemberIdAndCouponId(long memberId, long couponId);

    List<MemberCoupon> findAllByMemberId(long memberId);
}
