package coupon.repository;

import org.springframework.data.repository.Repository;

public interface IssuedCouponRepository extends Repository<IssuedCouponEntity, Long> {

    IssuedCouponEntity save(IssuedCouponEntity issuedCoupon);
}
