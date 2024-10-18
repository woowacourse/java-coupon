package coupon.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import coupon.coupon.domain.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
