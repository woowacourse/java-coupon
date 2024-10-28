package coupon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import coupon.repository.entity.MemberCouponEntity;

@Repository
public interface MemberCouponRepository extends JpaRepository<MemberCouponEntity, Long> {

    long countByMemberIdAndCouponId(long memberId, long couponId);

    List<MemberCouponEntity> findByMemberId(long memberId);
}
