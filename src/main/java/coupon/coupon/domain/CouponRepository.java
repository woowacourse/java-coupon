package coupon.coupon.domain;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface CouponRepository extends Repository<Coupon, Long> {

    void save(Coupon coupon);

    Optional<Coupon> findById(Long id);

    List<Coupon> findAll();

    List<Coupon> findAllByIdIn(List<Long> ids);
}
