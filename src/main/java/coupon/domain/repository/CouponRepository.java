package coupon.domain.repository;

import coupon.domain.Coupon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    List<Coupon> findByIdIn(List<Long> couponIds);
}
