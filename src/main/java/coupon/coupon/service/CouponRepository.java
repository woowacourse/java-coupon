package coupon.coupon.service;

import coupon.coupon.domain.Coupon;
import java.util.Optional;

public interface CouponRepository {

    Coupon save(Coupon coupon);

    Optional<Coupon> findById(long couponId);
}
