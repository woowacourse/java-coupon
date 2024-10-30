package coupon.data.repository;

import coupon.data.MemberCouponEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCouponEntity,Long> {
    List<MemberCouponEntity> findAllByMemberIdAndCouponId(long memberId, long couponId);

    List<MemberCouponEntity> findAllByMemberId(long memberId);
}
