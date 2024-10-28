package coupon.repository;

import coupon.entity.MemberCouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberCouponRepository extends JpaRepository<MemberCouponEntity, Long> {

    int countByCouponIdAndMemberId(long couponId, long memberId);

    List<MemberCouponEntity> findAllByMemberId(long id);
}
