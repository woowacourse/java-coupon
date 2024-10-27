package coupon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.domain.coupon.Coupon;

@Repository
public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {
    List<MemberCoupon> findByMemberAndCoupon(Member member, Coupon coupon);
}
