package coupon.membercoupon.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponJpaRepository extends JpaRepository<MemberCouponEntity, Long> {

    // TODO : Coupon도 id로 조회하도록 개선
    int countAllByMemberIdAndCouponId(Long memberId, Long couponId);

    List<MemberCouponEntity> findAllByMemberId(Long memberId);
}
