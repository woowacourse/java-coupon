package coupon.infra.db.redis;

import coupon.infra.db.CouponEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface RedisCouponRepository extends CrudRepository<CouponEntity, Long> {

    List<CouponEntity> findAllByIdIn(List<Long> ids);
}
