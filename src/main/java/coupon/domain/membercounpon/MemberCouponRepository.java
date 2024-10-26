package coupon.domain.membercounpon;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    long countMemberCouponByMemberId(Long memberId);

    List<MemberCoupon> findAllByMemberId(Long memberId);
}
