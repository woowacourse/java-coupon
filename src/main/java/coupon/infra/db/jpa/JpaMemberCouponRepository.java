package coupon.infra.db.jpa;

import coupon.infra.db.MemberCouponEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberCouponRepository extends JpaRepository<MemberCouponEntity, Long> {

    List<MemberCouponEntity> findAllByMemberIdAndCouponId(Long memberId, Long couponId);

    List<MemberCouponEntity> findAllByMemberId(Long memberId);
}
