package coupon.infra.db.jpa;

import coupon.infra.db.CouponEntity;
import java.util.Optional;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCouponRepository extends JpaRepository<CouponEntity, Long> {

    @CachePut(value = "coupons", key = "#result.id")
    CouponEntity save(CouponEntity couponEntity);

    @Cacheable(value = "coupons", key = "#id")
    Optional<CouponEntity> findById(Long id);
}
