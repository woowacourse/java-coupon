package coupon.coupon.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    default Coupon fetchById(Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 쿠폰이 없습니다. id: " + id));
    }

    Optional<Coupon> findById(Long id);
}
