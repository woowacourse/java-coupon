package coupon.infra.db;

import org.springframework.data.repository.CrudRepository;

public interface RedisCouponRepository extends CrudRepository<CouponCache, Long> {
}
