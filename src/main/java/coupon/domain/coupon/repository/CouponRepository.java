package coupon.domain.coupon.repository;

import coupon.domain.coupon.Coupon;
import java.util.Optional;

public interface CouponRepository {

    Coupon save(Coupon coupon);

    Optional<Coupon> findById(Long id);
}
