package coupon.service.port;

import java.util.Optional;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponName;

public interface CouponRepository {

    void save(Coupon coupon);

    Optional<Coupon> findByName(CouponName name);
}
