package coupon.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import coupon.domain.Coupon;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {
}
