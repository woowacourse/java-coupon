package coupon.repository;

import coupon.domain.Coupon;
import coupon.exception.CouponException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    default Coupon getByCouponId(long id) {
        return findById(id).orElseThrow(() -> new CouponException("쿠폰 정보가 존재하지 않습니다."));
    }
}
