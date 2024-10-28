package coupon.membercoupon.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    List<MemberCoupon> findByMemberId(Long memberId);

    Long countByMemberId(Long memberId);
}
