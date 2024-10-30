package coupon.repository;

import coupon.domain.Coupon;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    List<Coupon> findAllByIdIn(Set<Long> ids);
}
