package coupon.coupon.domain;

import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface CouponRepository extends Repository<Coupon, Long> {

    void save(Coupon coupon);

    Optional<Coupon> findById(Long id);
}
