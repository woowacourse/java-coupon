package coupon.repository.cache;

import coupon.entity.cache.CachedCouponEntity;
import org.springframework.data.repository.CrudRepository;

public interface CachedCouponRepository extends CrudRepository<CachedCouponEntity, Long> {
}
