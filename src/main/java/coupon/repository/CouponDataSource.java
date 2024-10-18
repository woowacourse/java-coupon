package coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import coupon.domain.Coupon;

public interface CouponDataSource extends JpaRepository<Coupon, Long> {
}
