package coupon.domain.member_coupon.repository;

import coupon.domain.member_coupon.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {
}
