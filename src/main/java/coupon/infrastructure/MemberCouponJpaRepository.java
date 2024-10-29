package coupon.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import coupon.domain.MemberCoupon;

public interface MemberCouponJpaRepository extends JpaRepository<MemberCoupon, Long> {
}
