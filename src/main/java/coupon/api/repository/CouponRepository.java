package coupon.api.repository;

import coupon.entity.Coupon;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends Repository<Coupon, Long> {

    List<Coupon> findAll();

    void save(Coupon coupon);

    Optional<Coupon> findCouponById(Long couponId);

    void deleteAll();
}
