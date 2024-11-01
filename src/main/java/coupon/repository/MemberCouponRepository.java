package coupon.repository;

import coupon.entity.MemberCouponEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberCouponRepository extends JpaRepository<MemberCouponEntity, Long> {

    List<MemberCouponEntity> findAllByMemberIdAndCouponId(Long memberId, Long couponId);

    List<MemberCouponEntity> findAllByMemberId(Long memberId);
}
