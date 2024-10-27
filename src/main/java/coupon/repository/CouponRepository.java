package coupon.repository;

import coupon.domain.Coupon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    List<Coupon> findAllByIdIn(List<Long> ids);
}
