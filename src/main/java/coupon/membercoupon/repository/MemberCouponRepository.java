package coupon.membercoupon.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import coupon.membercoupon.domain.MemberCoupon;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    List<MemberCoupon> findAllByMemberId(Long memberId);
}
