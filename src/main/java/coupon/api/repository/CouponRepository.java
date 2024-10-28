package coupon.api.repository;

import coupon.entity.Coupon;
import org.springframework.data.repository.Repository;

public interface CouponRepository extends Repository<Coupon, Long> {

    Coupon save(Coupon coupon);

}
