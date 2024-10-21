package coupon.infra.db.redis;

import coupon.infra.db.CouponEntity;
import java.util.List;
import java.util.Objects;
import org.springframework.data.repository.CrudRepository;

public interface RedisCouponRepository extends CrudRepository<CouponEntity, Long> {

    default List<CouponEntity> findAllByIdIn(List<Long> ids) {
        return ids.stream()
                .map(id -> findById(id).orElse(null))
                .filter(Objects::nonNull)
                .toList();
    }
}
