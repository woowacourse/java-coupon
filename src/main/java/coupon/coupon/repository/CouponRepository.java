package coupon.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import coupon.coupon.domain.entity.CouponEntity;

@Repository
public interface CouponRepository extends JpaRepository<CouponEntity, Long> {

}
