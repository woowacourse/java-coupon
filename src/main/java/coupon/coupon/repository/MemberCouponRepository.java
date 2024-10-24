package coupon.coupon.repository;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.MemberCoupon;
import coupon.member.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    List<MemberCoupon> findAllByCouponAndMember(Coupon coupon, Member member);
}
