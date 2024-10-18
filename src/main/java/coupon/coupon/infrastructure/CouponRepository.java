package coupon.coupon.infrastructure;

import coupon.coupon.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<CouponEntity, Long> {

}
