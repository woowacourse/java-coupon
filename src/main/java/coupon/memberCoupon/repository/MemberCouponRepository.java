package coupon.memberCoupon.repository;

import coupon.coupon.domain.Coupon;
import coupon.member.domain.Member;
import coupon.memberCoupon.domain.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    int countAllByCouponAndMember(Coupon targetCoupon, Member targetMember);
}
