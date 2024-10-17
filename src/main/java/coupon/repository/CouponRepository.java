package coupon.repository;

import org.springframework.data.repository.Repository;

public interface CouponRepository extends Repository<CouponEntity, Long> {

    CouponEntity save(CouponEntity coupon);

    CouponEntity findById(Long id);
}
