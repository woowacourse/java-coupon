package coupon.infra.db.redis;

import coupon.infra.db.CouponEntity;
import org.springframework.data.repository.CrudRepository;

public interface RedisCouponRepository extends CrudRepository<CouponEntity, Long> {
}
