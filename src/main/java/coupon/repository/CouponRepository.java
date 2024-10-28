package coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import coupon.repository.CouponEntity;

@Repository
public interface CouponRepository extends JpaRepository<CouponEntity, Long> {
}
