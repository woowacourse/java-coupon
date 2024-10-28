package coupon.memberCoupon.repository;

import coupon.memberCoupon.domain.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {
}
