package coupon.repository;

import coupon.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    default Coupon getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰 아이디에 해당하는 쿠폰이 없습니다. id: " + id));
    }
}
