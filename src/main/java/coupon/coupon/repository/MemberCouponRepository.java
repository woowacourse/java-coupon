package coupon.coupon.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.Member;
import coupon.coupon.domain.MemberCoupon;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    List<MemberCoupon> findAllByMemberAndCoupon(Member member, Coupon coupon);
}
