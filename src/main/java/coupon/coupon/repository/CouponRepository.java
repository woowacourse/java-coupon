package coupon.coupon.repository;

import coupon.coupon.domain.CouponEntity;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<CouponEntity, Long> {

    @Cacheable(value = "coupon")
    Optional<CouponEntity> findById(long id);
}
