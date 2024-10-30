package coupon.coupon.repository;

import coupon.coupon.entity.MemberCouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberCouponRepository extends JpaRepository<MemberCouponEntity, Long> {

    List<MemberCouponEntity> findByCouponIdAndMemberId(Long couponId, Long memberId);

    int countByCouponIdAndMemberId(Long couponId, Long memberId);

    List<MemberCouponEntity> findByMemberId(Long memberId);
}
