package coupon.repository;

import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface CouponRepository extends Repository<CouponEntity, Long> {

    CouponEntity save(CouponEntity coupon);

    Optional<CouponEntity> findById(Long id);
}
