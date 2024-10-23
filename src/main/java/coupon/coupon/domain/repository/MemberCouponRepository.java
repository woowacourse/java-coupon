package coupon.coupon.domain.repository;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.MemberCoupon;
import coupon.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    Optional<MemberCoupon> findByMemberAndCoupon(Member member, Coupon coupon);
}
