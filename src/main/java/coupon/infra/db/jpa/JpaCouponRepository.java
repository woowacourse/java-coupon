package coupon.infra.db.jpa;

import coupon.infra.db.CouponEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCouponRepository extends JpaRepository<CouponEntity, Long> {

    List<CouponEntity> findAllByIdIn(List<Long> ids);
}
