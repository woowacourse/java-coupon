package coupon.coupon.service.port;

import java.util.Optional;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponName;

public interface CouponRepository {

    void save(Coupon coupon);

    Optional<Coupon> findByName(CouponName name);
}
