package coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import coupon.domain.MemberCoupon;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {
}
