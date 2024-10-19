package coupon.domain.coupon.repository;

import coupon.domain.coupon.Coupon;
import coupon.exception.CouponNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    default Coupon getById(long id) {
        return findById(id)
                .orElseThrow(() -> new CouponNotFoundException("조회하신 쿠폰 정보가 존재하지 않습니다. (couponId: %d)"
                        .formatted(id)));
    }
}
