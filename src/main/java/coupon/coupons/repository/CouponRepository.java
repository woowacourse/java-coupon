package coupon.coupons.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import coupon.coupons.domain.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
