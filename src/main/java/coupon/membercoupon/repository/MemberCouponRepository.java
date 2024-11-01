package coupon.membercoupon.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCouponEntity, Long> {
    List<MemberCouponEntity> findAllByMemberId(Long memberId);
}
