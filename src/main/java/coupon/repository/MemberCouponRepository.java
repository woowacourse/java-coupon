package coupon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import coupon.domain.MemberCoupon;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    int countByMemberId(long memberId);

    List<MemberCoupon> findAllByMemberId(long memberId);
}
