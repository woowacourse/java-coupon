package coupon.repository;

import coupon.cache.CachedCoupon;
import org.springframework.data.repository.CrudRepository;

public interface CachedCouponRepository extends CrudRepository<CachedCoupon, Long> {
}
