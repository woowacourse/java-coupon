package coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import coupon.domain.coupon.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
