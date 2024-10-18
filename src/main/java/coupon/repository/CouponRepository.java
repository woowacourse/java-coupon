package coupon.repository;

import coupon.domain.Coupon;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CouponRepository extends CrudRepository<Coupon, UUID> {
}
