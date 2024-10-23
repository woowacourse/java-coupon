package coupon.repository;

import coupon.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponDataSource extends JpaRepository<Coupon, Long> {
}
