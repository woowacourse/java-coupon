package coupon.repository;

import coupon.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponDiskRepository extends JpaRepository<Coupon, Long> {
}
